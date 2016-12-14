/*
===================================================================
Copyright Dinamenta, UAB http://www.dhtmlx.com
This code is obfuscated and not allowed for any purposes except 
using on sites which belongs to Dinamenta, UAB

Please contact sales@dhtmlx.com to obtain necessary 
license for usage of dhtmlx components.
===================================================================
*/
function xmlPointer(a) {
	this.d = a
};
xmlPointer.prototype = {
	text: function() {
		if (!aq) return this.d.xml;
		var a = new XMLSerializer;
		return a.serializeToString(this.d)
	},
	get: function(a) {
		return this.d.getAttribute(a)
	},
	exists: function() {
		return ! !this.d
	},
	content: function() {
		return this.d.firstChild ? this.d.firstChild.data: ""
	},
	Kw: function(a, b, c, d) {
		var e = this.d.childNodes,
		f = new xmlPointer;
		if (e.length) for (d = d || 0; d < e.length; d++) if (e[d].tagName == a && (f.d = e[d], b.apply(c, [f, d]) == -1)) break
	},
	get_all: function() {
		for (var a = {},
		b = this.d.attributes,
		c = 0; c < b.length; c++) a[b[c].name] = b[c].value;
		return a
	},
	sub: function(a) {
		var b = this.d.childNodes,
		c = new xmlPointer;
		if (b.length) for (var d = 0; d < b.length; d++) if (b[d].tagName == a) return c.d = b[d],
		c
	},
	up: function() {
		return new xmlPointer(this.d.parentNode)
	},
	set: function(a, b) {
		this.d.setAttribute(a, b)
	},
	clone: function() {
		return new xmlPointer(this.d)
	},
	sub_exists: function(a) {
		var b = this.d.childNodes;
		if (b.length) for (var c = 0; c < b.length; c++) if (b[c].tagName == a) return ! 0;
		return ! 1
	},
	through: function(a, b, c, d, e) {
		var f = this.d.childNodes;
		if (f.length) for (var g = 0; g < f.length; g++) {
			if (f[g].tagName == a && f[g].getAttribute(b) != null && f[g].getAttribute(b) != "" && (!c || f[g].getAttribute(b) == c)) {
				var h = new xmlPointer(f[g]);
				d.apply(e, [h, g])
			}
			var j = this.d;
			this.d = f[g];
			this.through(a, b, c, d, e);
			this.d = j
		}
	}
};
function dhtmlXTreeObject(a, b, c, d) {
	if (_isIE) try {
		document.execCommand("BackgroundImageCache", !1, !0)
	} catch(e) {}
	this.parentObject = typeof a != "object" ? document.getElementById(a) : a;
	this.parentObject.style.overflow = "hidden";
	this._itim_dg = !0;
	this.dlmtr = ",";
	this.dropLower = !1;
	this.enableIEImageFix();
	this.xmlstate = 0;
	this.acE = "tree";
	this.smcheck = !0;
	this.width = b;
	this.height = c;
	this.fO = d;
	this.childCalc = null;
	this.def_line_img_y = this.def_line_img_x = this.def_img_y = this.def_img_x = "18px";
	this.jG = [];
	this._selected = [];
	this.style_pointer = "pointer";
	this._aimgs = !0;
	this.htmlcA = " [";
	this.htmlcB = "]";
	this.lWin = window;
	this.mlitems = this.cMenu = 0;
	this.iconURL = "";
	this.dadmode = 0;
	this.slowParse = !1;
	this.autoScroll = !0;
	this.hfMode = 0;
	this.nodeCut = [];
	this.XMLloadingWarning = this.XMLsource = 0;
	this.gQ = {};
	this._pullSize = 0;
	this.treeLinesOn = !0;
	this.tscheck = !1;
	this.timgen = !0;
	this.dpcpy = !1;
	this._ld_id = null;
	this._oie_onXLE = [];
	this.imPath = window.fe || "";
	this.checkArray = "iconUncheckAll.gif,iconCheckAll.gif,iconCheckGray.gif,iconUncheckDis.gif,iconCheckDis.gif,iconCheckDis.gif".split(",");
	this.radioArray = "radio_off.gif,radio_on.gif,radio_on.gif,radio_off.gif,radio_on.gif,radio_on.gif".split(",");
	this.lineArray = "line2.gif,line3.gif,line4.gif,blank.gif,blank.gif,line1.gif".split(",");
	this.minusArray = ["minus2.gif", "minus3.gif", "minus4.gif", "minus.gif", "minus5.gif"];
	this.plusArray = ["plus2.gif", "plus3.gif", "plus4.gif", "plus.gif", "plus5.gif"];
	this.imageArray = ["leaf.gif", "folderOpen.gif", "folderClosed.gif"];
	this.cutImg = [0, 0, 0];
	this.cutImage = "but_cut.gif";
	dhtmlxEventable(this);
	this.ae = new dhtmlDragAndDropObject;
	this.ao = new dhtmlXTreeItemObject(this.fO, "", 0, this);
	this.ao.ao.childNodes[0].childNodes[0].style.display = "none";
	this.ao.ao.childNodes[0].childNodes[0].childNodes[0].className = "hiddenRow";
	this.cB = this.eh();
	this.cB.appendChild(this.ao.ao);
	dhtmlx.$customScroll && dhtmlx.CustomScroll.enable(this);
	if (aq) this.cB.childNodes[0].width = "100%",
	this.cB.childNodes[0].style.overflow = "hidden";
	var f = this;
	this.cB.onselectstart = new Function("return false;");
	if (gP) this.cB.oncontextmenu = function(a) {
		return f._doContClick(a || window.event, !0)
	};
	this.cB.onmousedown = function(a) {
		return f._doContClick(a || window.event)
	};
	this.dF = new ag(this._parseXMLTree, this, !0, this.ir);
	_isIE && this.jJ(!0);
	this.selectionBar = document.createElement("DIV");
	this.selectionBar.className = "selectionBar";
	this.selectionBar.innerHTML = "&nbsp;";
	this.selectionBar.style.display = "none";
	this.cB.appendChild(this.selectionBar);
	window.addEventListener && window.addEventListener("unload",
	function() {
		try {
			f.fA()
		} catch(a) {}
	},
	!1);
	window.attachEvent && window.attachEvent("onunload",
	function() {
		try {
			f.fA()
		} catch(a) {}
	});
	this.setImagesPath = this.setImagePath;
	this.setIconsPath = this.setIconPath;
	dhtmlx.image_path && this.setImagePath(dhtmlx.image_path);
	dhtmlx.skin && this.setSkin(dhtmlx.skin);
	return this
}
dhtmlXTreeObject.prototype.setDataMode = function(a) {
	this._datamode = a
};
dhtmlXTreeObject.prototype._doContClick = function(a, b) {
	if (!b && a.button != 2) return this._acMenu && (this._acMenu.hideContextMenu ? this._acMenu.hideContextMenu() : this.cMenu.ES()),
	!0;
	for (var c = _isIE ? a.srcElement: a.target; c && c.tagName != "BODY";) {
		if (c.parentObject) break;
		c = c.parentNode
	}
	if (!c || !c.parentObject) return ! 0;
	var d = c.parentObject;
	if (!this.callEvent("onRightClick", [d.id, a]))(a.srcElement || a.target).oncontextmenu = function(a) { (a || event).cancelBubble = !0;
		return ! 1
	};
	if (this._acMenu = d.cMenu || this.cMenu) {
		if (!this.callEvent("onBeforeContextMenu", [d.id])) return ! 0;
		if (!gP)(a.srcElement || a.target).oncontextmenu = function(a) { (a || event).cancelBubble = !0;
			return ! 1
		};
		if (this._acMenu.showContextMenu) {
			var e = window.document.documentElement,
			f = window.document.body,
			g = [e.scrollLeft || f.scrollLeft, e.scrollTop || f.scrollTop];
			if (_isIE) var h = a.clientX + g[0],
			j = a.clientY + g[1];
			else h = a.pageX,
			j = a.pageY;
			this._acMenu.showContextMenu(h - 1, j - 1);
			this.contextID = d.id;
			a.cancelBubble = !0;
			this._acMenu._skip_hide = !0
		} else c.contextMenuId = d.id,
		c.contextMenu = this._acMenu,
		c.a = this._acMenu.vm,
		c.a(c, a),
		c.a = null;
		return ! 1
	}
	return ! 0
};
dhtmlXTreeObject.prototype.enableIEImageFix = function(a) {
	a ? (this._getImg = function() {
		var a = document.createElement("DIV");
		a.innerHTML = "&nbsp;";
		a.className = "dhx_bg_img_fix";
		return a
	},
	this._setSrc = function(a, c) {
		a.style.backgroundImage = "url(" + c + ")"
	},
	this._getSrc = function(a) {
		var c = a.style.backgroundImage;
		return c.substr(4, c.length - 5).replace(/(^")|("$)/g, "")
	}) : (this._getImg = function(a) {
		return document.createElement(a == this.fO ? "div": "img")
	},
	this._setSrc = function(a, c) {
		a.src = c
	},
	this._getSrc = function(a) {
		return a.src
	})
};
dhtmlXTreeObject.prototype.fA = function() {
	for (var a in this.gQ) {
		var b = this.gQ[a];
		if (b) b.parentObject = null,
		b.treeNod = null,
		b.childNodes = null,
		b.span = null,
		b.tr.nodem = null,
		b.tr = null,
		b.ao.objBelong = null,
		b.ao = null,
		this.gQ[a] = null
	}
	this.parentObject.innerHTML = "";
	this.dF && this.dF.fA();
	this.cB.onselectstart = null;
	this.cB.oncontextmenu = null;
	this.cB.onmousedown = null;
	for (a in this) this[a] = null
};
function cObject() {
	return this
};
cObject.prototype = {};
cObject.prototype.clone = function() {
	function a() {}
	a.prototype = this;
	return new a
};
function dhtmlXTreeItemObject(a, b, c, d, e, f) {
	this.mB = this.acolor = this.ao = "";
	this.span = this.dragMove = this.dragSpan = this.dG = this.dH = this.aE = this.tr = 0;
	this.closeble = 1;
	this.childNodes = [];
	this.userData = new cObject;
	this.checkstate = 0;
	this.treeNod = d;
	this.label = b;
	this.parentObject = c;
	this.actionHandler = e;
	this.images = [d.imageArray[0], d.imageArray[1], d.imageArray[2]];
	this.id = d._globalIdStorageAdd(a, this);
	this.ao = this.treeNod.checkBoxOff ? this.treeNod._createItem(1, this, f) : this.treeNod._createItem(0, this, f);
	this.ao.objBelong = this;
	return this
}
dhtmlXTreeObject.prototype._globalIdStorageAdd = function(a, b) {
	if (this.ak(a, 1, 1)) return a = a + "_" + (new Date).valueOf(),
	this._globalIdStorageAdd(a, b);
	this.gQ[a] = b;
	this._pullSize++;
	return a
};
dhtmlXTreeObject.prototype._globalIdStorageSub = function(a) {
	this.gQ[a] && (this._unselectItem(this.gQ[a]), this.gQ[a] = null, this._pullSize--);
	this._locker && this._locker[a] && (this._locker[a] = !1)
};
dhtmlXTreeObject.prototype.ak = function(a, b, c, d) {
	var e = this.gQ[a];
	if (e) {
		e.unParsed && !c && this.reParse(e, 0);
		this.ahK && !e.ao && this._buildSRND(e, c);
		if (d && this._edsbpsA) for (var f = 0; f < this._edsbpsA.length; f++) if (this._edsbpsA[f][2] == a) return dhtmlxError.gn("getItem", "Requested item still in parsing process.", a),
		null;
		return e
	}
	return this.slowParse && a != 0 && !b ? this.preParse(a) : null
};
dhtmlXTreeObject.prototype._getSubItemsXML = function(a) {
	var b = [];
	a.Kw("item",
	function(a) {
		b.push(a.get("id"))
	},
	this);
	return b.join(this.dlmtr)
};
dhtmlXTreeObject.prototype.enableSmartXMLParsing = function(a) {
	this.slowParse = ap(a)
};
dhtmlXTreeObject.prototype.findXML = function() {};
dhtmlXTreeObject.prototype._getAllCheckedXML = function(a, b, c) {
	var d = [];
	c == 2 && a.through("item", "checked", -1,
	function(a) {
		d.push(a.get("id"))
	},
	this);
	c == 1 && a.through("item", "id", null,
	function(a) {
		a.get("checked") && a.get("checked") != -1 && d.push(a.get("id"))
	},
	this);
	c == 0 && a.through("item", "id", null,
	function(a) { (!a.get("checked") || a.get("checked") == 0) && d.push(a.get("id"))
	},
	this);
	return d.length ? b + (b ? this.dlmtr: "") + d.join(this.dlmtr) : b ? b: ""
};
dhtmlXTreeObject.prototype._setSubCheckedXML = function(a, b) {
	var c = a ? "1": "";
	b.through("item", "id", null,
	function(a) { (!a.get("disabled") || a.get("disabled") == 0) && a.set("checked", c)
	},
	this)
};
dhtmlXTreeObject.prototype._getAllScraggyItemsXML = function(a) {
	var b = [],
	c = function(a) {
		a.sub_exists("item") ? a.Kw("item", c, this) : b.push(a.get("id"))
	};
	c(a);
	return b.join(",")
};
dhtmlXTreeObject.prototype._getAllFatItemsXML = function(a) {
	var b = [],
	c = function(a) {
		a.sub_exists("item") && (b.push(a.get("id")), a.Kw("item", c, this))
	};
	c(a);
	return b.join(",")
};
dhtmlXTreeObject.prototype._getAllSubItemsXML = function(a, b, c) {
	b = [];
	c.through("item", "id", null,
	function(a) {
		b.push(a.get("id"))
	},
	this);
	return b.join(",")
};
dhtmlXTreeObject.prototype.reParse = function(a) {
	var b = this;
	this.parsCount || b.callEvent("onXLS", [b, a.id]);
	this.xmlstate = 1;
	var c = a.unParsed;
	a.unParsed = 0;
	this.XMLloadingWarning = 1;
	var d = this.parsingOn,
	e = this.waitUpdateXML,
	f = this.parsedArray;
	this.parsedArray = [];
	this.waitUpdateXML = !1;
	this.parsingOn = a.id;
	this.parsedArray = [];
	this.setCheckList = "";
	this.Aq(c, a.id, 2);
	for (var g = this.setCheckList.split(this.dlmtr), h = 0; h < this.parsedArray.length; h++) a.ao.childNodes[0].appendChild(this.parsedArray[h]);
	c.get("order") && c.get("order") != "none" && this._reorderBranch(a, c.get("order"), !0);
	this.oldsmcheck = this.smcheck;
	this.smcheck = !1;
	for (var j = 0; j < g.length; j++) g[j] && this.setCheck(g[j], 1);
	this.smcheck = this.oldsmcheck;
	this.parsingOn = d;
	this.waitUpdateXML = e;
	this.parsedArray = f;
	this.XMLloadingWarning = 0;
	this._redrawFrom(this, a);
	this.ahK && !a._sready && this.prepareSR(a.id);
	this.xmlstate = 0;
	return ! 0
};
dhtmlXTreeObject.prototype.preParse = function(a) {
	if (!a || !this._p) return null;
	var b = !1;
	this._p.clone().through("item", "id", a,
	function(a) {
		this.ak(a.up().get("id"));
		return b = !0
	},
	this);
	if (b) {
		var c = this.ak(a, !0, !1);
		c || dhtmlxError.gn("getItem", "The item " + a + " not operable. Seems you have non-unique|incorrect IDs in tree's XML.", a)
	}
	return c
};
dhtmlXTreeObject.prototype._escape = function(a) {
	switch (this.utfesc) {
	case "none":
		return a;
	case "utf8":
		return encodeURIComponent(a);
	default:
		return escape(a)
	}
};
dhtmlXTreeObject.prototype._drawNewTr = function(a) {
	var b = document.createElement("tr"),
	c = document.createElement("td"),
	d = document.createElement("td");
	c.appendChild(document.createTextNode(" "));
	d.colSpan = 3;
	d.appendChild(a);
	b.appendChild(c);
	b.appendChild(d);
	return b
};
dhtmlXTreeObject.prototype.loadXMLString = function(a, b) {
	var c = this;
	this.parsCount || this.callEvent("onXLS", [c, null]);
	this.xmlstate = 1;
	if (b) this.dF.aA = b;
	this.dF.loadXMLString(a)
};
dhtmlXTreeObject.prototype.loadXML = function(a, b) {
	if (this._datamode && this._datamode != "xml") return this["load" + this._datamode.toUpperCase()](a, b);
	var c = this;
	this.parsCount || this.callEvent("onXLS", [c, this._ld_id]);
	this._ld_id = null;
	this.xmlstate = 1;
	this.dF = new ag(this._parseXMLTree, this, !0, this.ir);
	if (b) this.dF.aA = b;
	this.dF.loadXML(a)
};
dhtmlXTreeObject.prototype._attachChildNode = function(a, b, c, d, e, f, g, h, j, i, m) {
	if (i && i.parentObject) a = i.parentObject;
	if (a.XMLload == 0 && this.XMLsource && !this.XMLloadingWarning) a.XMLload = 1,
	this._loadDynXML(a.id);
	var l = a.aE,
	k = a.childNodes;
	if (m && m.tr.previousSibling) m.tr.previousSibling.previousSibling ? i = m.tr.previousSibling.nodem: h = h.replace("TOP", "") + ",TOP";
	if (i) {
		var o, r;
		for (o = 0; o < l; o++) if (k[o] == i) {
			for (r = l; r != o; r--) k[1 + r] = k[r];
			break
		}
		o++;
		l = o
	}
	if (h) for (var s = h.split(","), p = 0; p < s.length; p++) switch (s[p]) {
	case "TOP":
		if (a.aE > 0) i = {},
		i.tr = a.childNodes[0].tr.previousSibling;
		a._has_top = !0;
		for (o = l; o > 0; o--) k[o] = k[o - 1];
		l = 0
	}
	var n;
	if (! (n = this.gQ[b]) || n.span != -1) n = k[l] = new dhtmlXTreeItemObject(b, c, a, this, d, 1),
	b = k[l].id,
	a.aE++;
	if (!n.ao) n.label = c,
	n.ao = this._createItem(this.checkBoxOff ? 1 : 0, n),
	n.ao.objBelong = n;
	e && (n.images[0] = e);
	f && (n.images[1] = f);
	g && (n.images[2] = g);
	var q = this._drawNewTr(n.ao);
	if (this.XMLloadingWarning || this._hAdI) n.ao.parentNode.parentNode.style.display = "none";
	i && i.tr && i.tr.nextSibling ? a.ao.childNodes[0].insertBefore(q, i.tr.nextSibling) : this.parsingOn == a.id ? this.parsedArray[this.parsedArray.length] = q: a.ao.childNodes[0].appendChild(q);
	i && !i.span && (i = null);
	if (this.XMLsource) n.XMLload = j && j != 0 ? 0 : 1;
	n.tr = q;
	q.nodem = n;
	if (a.itemId == 0) q.childNodes[0].className = "hiddenRow"; (a._r_logic || this._frbtr) && this._setSrc(n.ao.childNodes[0].childNodes[0].childNodes[1].childNodes[0], this.imPath + this.radioArray[0]);
	if (h) {
		s = h.split(",");
		for (p = 0; p < s.length; p++) switch (s[p]) {
		case "SELECT":
			this.hE(b, !1);
			break;
		case "CALL":
			this.hE(b, !0);
			break;
		case "CHILD":
			n.XMLload = 0;
			break;
		case "CHECKED":
			this.XMLloadingWarning ? this.setCheckList += this.dlmtr + b: this.setCheck(b, 1);
			break;
		case "HCHECKED":
			this._setCheck(n, "unsure");
			break;
		case "OPEN":
			n.openMe = 1
		}
	}
	if (!this.XMLloadingWarning && (this._getOpenState(a) < 0 && !this._hAdI && this.hx(a.id), i && (this._correctPlus(i), this._correctLine(i)), this._correctPlus(a), this._correctLine(a), this._correctPlus(n), a.aE >= 2 && (this._correctPlus(k[a.aE - 2]), this._correctLine(k[a.aE - 2])), a.aE != 2 && this._correctPlus(k[0]), this.tscheck && this._correctCheckStates(a), this.BX)) if (this.xmlstate == 1) {
		var t = this.onXLE;
		this.onXLE = function(a) {
			this.BX(b);
			t && t(a)
		}
	} else this.BX(b);
	return n
};
dhtmlXTreeObject.prototype.enableContextMenu = function(a) {
	if (a) this.cMenu = a
};
dhtmlXTreeObject.prototype.setItemContextMenu = function(a, b) {
	for (var c = a.toString().split(this.dlmtr), d = 0; d < c.length; d++) {
		var e = this.ak(c[d]);
		if (e) e.cMenu = b
	}
};
dhtmlXTreeObject.prototype.insertNewItem = function(a, b, c, d, e, f, g, h, j) {
	var i = this.ak(a);
	if (!i) return - 1;
	var m = this._attachChildNode(i, b, c, d, e, f, g, h, j);
	if (!this.gQ[this.fO].XMLload) this.gQ[this.fO].XMLload = 1; ! this.XMLloadingWarning && this.childCalc && this._fixChildCountLabel(i);
	return m
};
dhtmlXTreeObject.prototype.cH = function(a, b, c, d, e, f, g, h, j) {
	return this.insertNewItem(a, b, c, d, e, f, g, h, j)
};
dhtmlXTreeObject.prototype._parseXMLTree = function(a, b, c, d, e) {
	var f = new xmlPointer(e.cR("tree"));
	a.Aq(f);
	a._p = f
};
dhtmlXTreeObject.prototype._parseItem = function(a, b, c, d) {
	var e;
	if (this.ahK && (!this.gQ[e = a.get("id")] || !this.gQ[e].span)) this._addItemSRND(b.id, e, a);
	else {
		var f = a.get_all();
		if (typeof this.waitUpdateXML == "object" && !this.waitUpdateXML[f.id]) this.Aq(a, f.id, 1);
		else {
			if (f.text === null || typeof f.text == "undefined") if (f.text = a.sub("itemtext"), f.text) f.text = f.text.content();
			var g = [];
			f.select && g.push("SELECT");
			f.top && g.push("TOP");
			if (f.call) this.nodeAskingCall = f.id;
			f.checked == -1 ? g.push("HCHECKED") : f.checked && g.push("CHECKED");
			f.open && g.push("OPEN");
			if (this.waitUpdateXML) if (this.ak(f.id)) var h = this.updateItem(f.id, f.text, f.im0, f.im1, f.im2, f.checked, f.child);
			else this.npl == 0 ? g.push("TOP") : c = b.childNodes[this.npl],
			h = this._attachChildNode(b, f.id, f.text, 0, f.im0, f.im1, f.im2, g.join(","), f.child, 0, c),
			f.id = h.id,
			c = null;
			else h = this._attachChildNode(b, f.id, f.text, 0, f.im0, f.im1, f.im2, g.join(","), f.child, d || 0, c);
			if (f.tooltip) h.span.parentNode.parentNode.title = f.tooltip;
			f.style && (h.span.style.cssText ? h.span.style.cssText += ";" + f.style: h.span.setAttribute("style", h.span.getAttribute("style") + "; " + f.style));
			if (f.radio) h._r_logic = !0;
			if (f.nocheckbox) {
				var j = h.span.parentNode.previousSibling.previousSibling;
				j.childNodes[0].style.display = "none";
				if (window._KHTMLrv) j.style.display = "none";
				h.nocheckbox = !0
			}
			f.disabled && (f.checked != null && this._setCheck(h, f.checked), this.disableCheckbox(h, 1));
			h._acc = f.child || 0;
			this.parserExtension && this.parserExtension._parseExtension.call(this, a, f, b ? b.id: 0);
			this.setItemColor(h, f.aCol, f.sCol);
			f.locked == "1" && this.lockItem(h.id, !0, !0); (f.imwidth || f.imheight) && this.yZ(f.imwidth, f.imheight, h); (f.closeable == "0" || f.closeable == "1") && this.setItemCloseable(h, f.closeable);
			var i = "";
			f.topoffset && this.setItemTopOffset(h, f.topoffset);
			if (!this.slowParse || typeof this.waitUpdateXML == "object") a.sub_exists("item") && (i = this.Aq(a, f.id, 1));
			else {
				if (!h.aE && a.sub_exists("item")) h.unParsed = a.clone();
				a.Kw("userdata",
				function(a) {
					this.setUserData(f.id, a.get("name"), a.content())
				},
				this)
			}
			if (i != "") this.nodeAskingCall = i;
			a.Kw("userdata",
			function(b) {
				this.setUserData(a.get("id"), b.get("name"), b.content())
			},
			this)
		}
	}
};
dhtmlXTreeObject.prototype.Aq = function(a, b, c, d) {
	if (this.ahK && !this.parentObject.offsetHeight) {
		var e = this;
		return window.setTimeout(function() {
			e.Aq(a, b, c, d)
		},
		100)
	}
	if (a.exists()) {
		this.skipLock = !0;
		if (!b) {
			var b = a.get("id"),
			f = a.get("dhx_security");
			if (f) dhtmlx.security_key = f;
			if (a.get("radio")) this.ao._r_logic = !0;
			this.parsingOn = b;
			this.parsedArray = [];
			this.nodeAskingCall = this.setCheckList = ""
		}
		var g = this.ak(b);
		if (!g) return dhtmlxError.gn("DataStructure", "XML refers to not existing parent");
		this.parsCount = this.parsCount ? this.parsCount + 1 : 1;
		this.XMLloadingWarning = 1;
		var h = g.aE && !d && !this._edsbps && !g._has_top ? 0 : 0;
		this.npl = 0;
		a.Kw("item",
		function(d, e) {
			g.XMLload = 1;
			this._parseItem(d, g, 0, h);
			if (this._edsbps && this.npl == this._edsbpsC) return this._distributedStart(a, e + 1, b, c, g.aE),
			-1;
			this.npl++
		},
		this, d);
		if (!c) {
			a.Kw("userdata",
			function(b) {
				this.setUserData(a.get("id"), b.get("name"), b.content())
			},
			this);
			g.XMLload = 1;
			if (this.waitUpdateXML) {
				this.waitUpdateXML = !1;
				for (var j = g.aE - 1; j >= 0; j--) g.childNodes[j]._dmark && this.deleteItem(g.childNodes[j].id)
			}
			for (var i = this.ak(this.parsingOn), j = 0; j < this.parsedArray.length; j++) g.ao.childNodes[0].appendChild(this.parsedArray[j]);
			this.parsedArray = [];
			this.lastLoadedXMLId = b;
			this.XMLloadingWarning = 0;
			for (var m = this.setCheckList.split(this.dlmtr), l = 0; l < m.length; l++) m[l] && this.setCheck(m[l], 1);
			this.XMLsource && this.tscheck && this.smcheck && g.id != this.fO && (g.checkstate === 0 ? this._setSubChecked(0, g) : g.checkstate === 1 && this._setSubChecked(1, g));
			this._redrawFrom(this, null, d);
			a.get("order") && a.get("order") != "none" && this._reorderBranch(g, a.get("order"), !0);
			this.nodeAskingCall != "" && this.callEvent("onClick", [this.nodeAskingCall, this.ah()]);
			this._branchUpdate && this._branchUpdateNext(a)
		}
		if (this.parsCount == 1) {
			this.parsingOn = null;
			this.ahK && g.id != this.fO && (this.prepareSR(g.id), this.XMLsource && this.hx(g.id));
			a.through("item", "open", null,
			function(a) {
				this.hx(a.get("id"))
			},
			this);
			if (!this._edsbps || !this._edsbpsA.length) {
				var k = this;
				window.setTimeout(function() {
					k.callEvent("onXLE", [k, b])
				},
				1);
				this.xmlstate = 0
			}
			this.skipLock = !1
		}
		this.parsCount--;
		k = this;
		this._edsbps && window.setTimeout(function() {
			k._distributedStep(b)
		},
		this._edsbpsD);
		if (!c && this.onXLE) this.onXLE(this, b);
		return this.nodeAskingCall
	}
};
dhtmlXTreeObject.prototype._branchUpdateNext = function(a) {
	a.Kw("item",
	function(a) {
		var c = a.get("id");
		if (!this.gQ[c] || this.gQ[c].XMLload) this._branchUpdate++,
		this.smartRefreshItem(a.get("id"), a)
	},
	this);
	this._branchUpdate--
};
dhtmlXTreeObject.prototype.checkUserData = function(a, b) {
	if (a.nodeType == 1 && a.tagName == "userdata") {
		var c = a.getAttribute("name");
		c && a.childNodes[0] && this.setUserData(b, c, a.childNodes[0].data)
	}
};
dhtmlXTreeObject.prototype._redrawFrom = function(a, b, c, d) {
	if (b) e = b;
	else {
		var e = a.ak(a.lastLoadedXMLId);
		a.lastLoadedXMLId = -1;
		if (!e) return 0
	}
	for (var f = 0,
	g = c ? c - 1 : 0; g < e.aE; g++) {
		if (!this._branchUpdate || this._getOpenState(e) == 1) if (!b || d == 1) e.childNodes[g].ao.parentNode.parentNode.style.display = "";
		if (e.childNodes[g].openMe == 1) this._openItem(e.childNodes[g]),
		e.childNodes[g].openMe = 0;
		a._redrawFrom(a, e.childNodes[g]);
		if (this.childCalc != null) {
			if (e.childNodes[g].unParsed || !e.childNodes[g].XMLload && this.XMLsource) e.childNodes[g].span.innerHTML = e.childNodes[g]._acc ? e.childNodes[g].label + this.htmlcA + e.childNodes[g]._acc + this.htmlcB: e.childNodes[g].label;
			if (e.childNodes[g].childNodes.length && this.childCalc) {
				if (this.childCalc == 1) e.childNodes[g].span.innerHTML = e.childNodes[g].label + this.htmlcA + e.childNodes[g].aE + this.htmlcB;
				if (this.childCalc == 2) {
					var h = e.childNodes[g].aE - (e.childNodes[g].pureChilds || 0);
					if (h) e.childNodes[g].span.innerHTML = e.childNodes[g].label + this.htmlcA + h + this.htmlcB;
					e.pureChilds ? e.pureChilds++:e.pureChilds = 1
				}
				if (this.childCalc == 3) e.childNodes[g].span.innerHTML = e.childNodes[g].label + this.htmlcA + e.childNodes[g]._acc + this.htmlcB;
				if (this.childCalc == 4 && (h = e.childNodes[g]._acc)) e.childNodes[g].span.innerHTML = e.childNodes[g].label + this.htmlcA + h + this.htmlcB
			} else this.childCalc == 4 && f++;
			f += e.childNodes[g]._acc;
			this.childCalc == 3 && f++
		}
	}
	if (!e.unParsed && (e.XMLload || !this.XMLsource)) e._acc = f;
	a._correctLine(e);
	a._correctPlus(e);
	this.childCalc && !b && a._fixChildCountLabel(e)
};
dhtmlXTreeObject.prototype.eh = function() {
	var a = document.createElement("div");
	a.className = "containerTableStyle";
	a.style.width = this.width;
	a.style.height = this.height;
	this.parentObject.appendChild(a);
	return a
};
dhtmlXTreeObject.prototype._xcloseAll = function(a) {
	if (!a.unParsed) {
		if (this.fO != a.id) {
			if (!a.ao) return;
			for (var b = a.ao.childNodes[0].childNodes, c = b.length, d = 1; d < c; d++) b[d].style.display = "none";
			this._correctPlus(a)
		}
		for (d = 0; d < a.aE; d++) a.childNodes[d].aE && this._xcloseAll(a.childNodes[d])
	}
};
dhtmlXTreeObject.prototype._xopenAll = function(a) {
	this._HideShow(a, 2);
	for (var b = 0; b < a.aE; b++) this._xopenAll(a.childNodes[b])
};
dhtmlXTreeObject.prototype._correctPlus = function(a) {
	if (a.ao) {
		var b = a.ao.childNodes[0].childNodes[0].childNodes[0].lastChild,
		c = a.ao.childNodes[0].childNodes[0].childNodes[2].childNodes[0],
		d = this.lineArray;
		if (this.XMLsource && !a.XMLload) {
			if (d = this.plusArray, this._setSrc(c, this.iconURL + a.images[2]), this._txtimg) return b.innerHTML = "[+]"
		} else if (a.aE || a.unParsed) if (a.ao.childNodes[0].childNodes[1] && a.ao.childNodes[0].childNodes[1].style.display != "none") {
			if (!a.wsign) d = this.minusArray;
			this._setSrc(c, this.iconURL + a.images[1]);
			if (this._txtimg) return b.innerHTML = "[-]"
		} else {
			if (!a.wsign) d = this.plusArray;
			this._setSrc(c, this.iconURL + a.images[2]);
			if (this._txtimg) return b.innerHTML = "[+]"
		} else this._setSrc(c, this.iconURL + a.images[0]);
		var e = 2;
		a.treeNod.treeLinesOn ? (a.parentObject && (e = this._getCountStatus(a.id, a.parentObject)), this._setSrc(b, this.imPath + d[e])) : this._setSrc(b, this.imPath + d[3])
	}
};
dhtmlXTreeObject.prototype._correctLine = function(a) {
	if (a.ao) {
		var b = a.parentObject;
		if (b) if (this._getLineStatus(a.id, b) == 0 || !this.treeLinesOn) for (var c = 1; c <= a.aE; c++) {
			if (!a.ao.childNodes[0].childNodes[c]) break;
			a.ao.childNodes[0].childNodes[c].childNodes[0].style.backgroundImage = "";
			a.ao.childNodes[0].childNodes[c].childNodes[0].style.backgroundRepeat = ""
		} else for (c = 1; c <= a.aE; c++) {
			if (!a.ao.childNodes[0].childNodes[c]) break;
			a.ao.childNodes[0].childNodes[c].childNodes[0].style.backgroundImage = "url(" + this.imPath + this.lineArray[5] + ")";
			a.ao.childNodes[0].childNodes[c].childNodes[0].style.backgroundRepeat = "repeat-y"
		}
	}
};
dhtmlXTreeObject.prototype._getCountStatus = function(a, b) {
	return b.aE <= 1 ? b.id == this.fO ? 4 : 0 : b.childNodes[0].id == a ? b.id == this.fO ? 2 : 1 : b.childNodes[b.aE - 1].id == a ? 0 : 1
};
dhtmlXTreeObject.prototype._getLineStatus = function(a, b) {
	return b.childNodes[b.aE - 1].id == a ? 0 : 1
};
dhtmlXTreeObject.prototype._HideShow = function(a, b) {
	if (this.XMLsource && !a.XMLload) {
		if (b != 1) a.XMLload = 1,
		this._loadDynXML(a.id)
	} else {
		a.unParsed && this.reParse(a);
		var c = a.ao.childNodes[0].childNodes,
		d = c.length;
		if (d > 1) { (c[1].style.display != "none" || b == 1) && b != 2 ? (this.cB.childNodes[0].border = "1", this.cB.childNodes[0].border = "0", nodestyle = "none") : nodestyle = "";
			for (var e = 1; e < d; e++) c[e].style.display = nodestyle
		}
		this._correctPlus(a)
	}
};
dhtmlXTreeObject.prototype._getOpenState = function(a) {
	if (!a.ao) return 0;
	var b = a.ao.childNodes[0].childNodes;
	return b.length <= 1 ? 0 : b[1].style.display != "none" ? 1 : -1
};
dhtmlXTreeObject.prototype.onRowClick2 = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onDblClick", [this.parentObject.id, a])) return ! 1;
	this.parentObject.closeble && this.parentObject.closeble != "0" ? a._HideShow(this.parentObject) : a._HideShow(this.parentObject, 2);
	if (a.mR("onOpenEnd")) a.xmlstate ? (a._oie_onXLE.push(a.onXLE), a.onXLE = a._epnFHe) : a.callEvent("onOpenEnd", [this.parentObject.id, a._getOpenState(this.parentObject)]);
	return ! 1
};
dhtmlXTreeObject.prototype.onRowClick = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onOpenStart", [this.parentObject.id, a._getOpenState(this.parentObject)])) return 0;
	this.parentObject.closeble && this.parentObject.closeble != "0" ? a._HideShow(this.parentObject) : a._HideShow(this.parentObject, 2);
	if (a.mR("onOpenEnd")) a.xmlstate ? (a._oie_onXLE.push(a.onXLE), a.onXLE = a._epnFHe) : a.callEvent("onOpenEnd", [this.parentObject.id, a._getOpenState(this.parentObject)])
};
dhtmlXTreeObject.prototype._epnFHe = function(a, b, c) {
	b != this.fO && this.callEvent("onOpenEnd", [b, a.getOpenState(b)]);
	a.onXLE = a._oie_onXLE.pop();
	if (!c && !a._oie_onXLE.length && a.onXLE) a.onXLE(a, b)
};
dhtmlXTreeObject.prototype.onRowClickDown = function(a) {
	var a = a || window.event,
	b = this.parentObject.treeNod;
	b._selectItem(this.parentObject, a)
};
dhtmlXTreeObject.prototype.ah = function() {
	for (var a = [], b = 0; b < this._selected.length; b++) a[b] = this._selected[b].id;
	return a.join(this.dlmtr)
};
dhtmlXTreeObject.prototype._selectItem = function(a, b) {
	if (this.mR("onSelect")) this._onSSCFold = this.ah(); (!this._amsel || !b || !b.ctrlKey && !b.metaKey && !b.shiftKey) && this._unselectItems();
	if (a.i_sel && this._amsel && b && (b.ctrlKey || b.metaKey)) this._unselectItem(a);
	else if (!a.i_sel && (!this._amselS || this._selected.length == 0 || this._selected[0].parentObject == a.parentObject)) if (this._amsel && b && b.shiftKey && this._selected.length != 0 && this._selected[this._selected.length - 1].parentObject == a.parentObject) {
		var c = this._getIndex(this._selected[this._selected.length - 1]),
		d = this._getIndex(a);
		if (d < c) var e = c,
		c = d,
		d = e;
		for (var f = c; f <= d; f++) a.parentObject.childNodes[f].i_sel || this._markItem(a.parentObject.childNodes[f])
	} else this._markItem(a);
	if (this.mR("onSelect")) {
		var g = this.ah();
		g != this._onSSCFold && this.callEvent("onSelect", [g])
	}
};
dhtmlXTreeObject.prototype._markItem = function(a) {
	if (a.mB) a.span.style.color = a.mB;
	a.span.className = "selectedTreeRow";
	a.i_sel = !0;
	this._selected[this._selected.length] = a
};
dhtmlXTreeObject.prototype.akg = function(a) {
	var b = this.ak(a);
	return ! b ? null: this._getIndex(b)
};
dhtmlXTreeObject.prototype._getIndex = function(a) {
	for (var b = a.parentObject,
	c = 0; c < b.aE; c++) if (b.childNodes[c] == a) return c
};
dhtmlXTreeObject.prototype._unselectItem = function(a) {
	if (a && a.i_sel) {
		a.span.className = "standartTreeRow";
		if (a.acolor) a.span.style.color = a.acolor;
		a.i_sel = !1;
		for (var b = 0; b < this._selected.length; b++) if (!this._selected[b].i_sel) {
			this._selected.splice(b, 1);
			break
		}
	}
};
dhtmlXTreeObject.prototype._unselectItems = function() {
	for (var a = 0; a < this._selected.length; a++) {
		var b = this._selected[a];
		b.span.className = "standartTreeRow";
		if (b.acolor) b.span.style.color = b.acolor;
		b.i_sel = !1
	}
	this._selected = []
};
dhtmlXTreeObject.prototype.onRowSelect = function(a, b, c) {
	var a = a || window.event,
	d = this.parentObject;
	if (b) d = b.parentObject;
	var e = d.treeNod,
	f = e.ah(); (!a || !a.skipUnSel) && e._selectItem(d, a);
	c || (d.actionHandler ? d.actionHandler(d.id, f) : e.callEvent("onClick", [d.id, f]))
};
dhtmlXTreeObject.prototype._correctCheckStates = function(a) {
	if (this.tscheck && a && a.id != this.fO) {
		var b = a.childNodes,
		c = 0,
		d = 0;
		if (a.aE != 0) {
			for (var e = 0; e < a.aE; e++) if (!b[e].dscheck) if (b[e].checkstate == 0) c = 1;
			else if (b[e].checkstate == 1) d = 1;
			else {
				d = c = 1;
				break
			}
			c && d ? this._setCheck(a, "unsure") : c ? this._setCheck(a, !1) : this._setCheck(a, !0);
			this._correctCheckStates(a.parentObject)
		}
	}
};
dhtmlXTreeObject.prototype.onCheckBoxClick = function() {
	if (this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id, this.parentObject.checkstate])) {
		if (this.parentObject.dscheck) return ! 0;
		this.treeNod.tscheck ? this.parentObject.checkstate == 1 ? this.treeNod._setSubChecked(!1, this.parentObject) : this.treeNod._setSubChecked(!0, this.parentObject) : this.parentObject.checkstate == 1 ? this.treeNod._setCheck(this.parentObject, !1) : this.treeNod._setCheck(this.parentObject, !0);
		this.treeNod._correctCheckStates(this.parentObject.parentObject);
		return this.treeNod.callEvent("onCheck", [this.parentObject.id, this.parentObject.checkstate])
	}
};
dhtmlXTreeObject.prototype._createItem = function(a, b, c) {
	var d = document.createElement("table");
	d.cellSpacing = 0;
	d.cellPadding = 0;
	d.border = 0;
	if (this.hfMode) d.style.tableLayout = "fixed";
	d.style.margin = 0;
	d.style.padding = 0;
	var e = document.createElement("tbody"),
	f = document.createElement("tr"),
	g = document.createElement("td");
	g.className = "standartTreeImage";
	if (this._txtimg) {
		var h = document.createElement("div");
		g.appendChild(h);
		h.className = "dhx_tree_textSign"
	} else {
		h = this._getImg(b.id);
		h.border = "0";
		if (h.tagName == "IMG") h.align = "absmiddle";
		g.appendChild(h);
		h.style.padding = 0;
		h.style.margin = 0;
		h.style.width = this.def_line_img_x;
		h.style.height = this.def_line_img_y
	}
	var j = document.createElement("td"),
	i = this._getImg(this.cBROf ? this.fO: b.id);
	i.checked = 0;
	this._setSrc(i, this.imPath + this.checkArray[0]);
	i.style.width = "16px";
	i.style.height = "16px";
	if (!a)(!_isIE ? j: i).style.display = "none";
	j.appendChild(i);
	if (!this.cBROf && i.tagName == "IMG") i.align = "absmiddle";
	i.onclick = this.onCheckBoxClick;
	i.treeNod = this;
	i.parentObject = b;
	j.width = window._KHTMLrv ? "16px": "20px";
	var m = document.createElement("td");
	m.className = "standartTreeImage";
	var l = this._getImg(this.timgen ? b.id: this.fO);
	l.onmousedown = this._preventNsDrag;
	l.ondragstart = this._preventNsDrag;
	l.border = "0";
	if (this._aimgs) {
		l.parentObject = b;
		if (l.tagName == "IMG") l.align = "absmiddle";
		l.onclick = this.onRowSelect
	}
	c || this._setSrc(l, this.iconURL + this.imageArray[0]);
	m.appendChild(l);
	l.style.padding = 0;
	l.style.margin = 0;
	if (this.timgen) m.style.width = l.style.width = this.def_img_x,
	l.style.height = this.def_img_y;
	else if (l.style.width = "0px", l.style.height = "0px", cU || window._KHTMLrv) m.style.display = "none";
	var k = document.createElement("td");
	k.className = "standartTreeRow";
	b.span = document.createElement("span");
	b.span.className = "standartTreeRow";
	this.mlitems ? (b.span.style.width = this.mlitems, b.span.style.display = "block") : k.noWrap = !0;
	if (_isIE && _isIE > 7) k.style.width = "999999px";
	else if (!window._KHTMLrv) k.style.width = "100%";
	b.span.innerHTML = b.label;
	k.appendChild(b.span);
	k.parentObject = b;
	g.parentObject = b;
	k.onclick = this.onRowSelect;
	g.onclick = this.onRowClick;
	k.ondblclick = this.onRowClick2;
	if (this.ettip) f.title = b.label;
	if (this.nz) {
		if (this._aimgs) this.ae.dS(m, this),
		m.parentObject = b;
		this.ae.dS(k, this)
	}
	b.span.style.paddingLeft = "5px";
	b.span.style.paddingRight = "5px";
	k.style.verticalAlign = "";
	k.style.fontSize = "10pt";
	k.style.cursor = this.style_pointer;
	f.appendChild(g);
	f.appendChild(j);
	f.appendChild(m);
	f.appendChild(k);
	e.appendChild(f);
	d.appendChild(e);
	if (this.ehlt || this.mR("onMouseIn") || this.mR("onMouseOut")) f.onmousemove = this._itemMouseIn,
	f[_isIE ? "onmouseleave": "onmouseout"] = this._itemMouseOut;
	return d
};
dhtmlXTreeObject.prototype.setImagePath = function(a) {
	this.iconURL = this.imPath = a
};
dhtmlXTreeObject.prototype.setIconPath = function(a) {
	this.iconURL = a
};
dhtmlXTreeObject.prototype._getLeafCount = function(a) {
	for (var b = 0,
	c = 0; c < a.aE; c++) a.childNodes[c].aE == 0 && b++;
	return b
};
dhtmlXTreeObject.prototype._getChildCounterValue = function(a) {
	var b = this.ak(a);
	if (!b) return 0;
	if (b.unParsed || !b.XMLload && this.XMLsource) return b._acc;
	switch (this.childCalc) {
	case 1:
		return b.aE;
	case 2:
		return this._getLeafCount(b);
	case 3:
		return b._acc;
	case 4:
		return b._acc
	}
};
dhtmlXTreeObject.prototype._fixChildCountLabel = function(a) {
	if (this.childCalc != null) if (a.unParsed || !a.XMLload && this.XMLsource) a.span.innerHTML = a._acc ? a.label + this.htmlcA + a._acc + this.htmlcB: a.label;
	else switch (this.childCalc) {
	case 1:
		a.span.innerHTML = a.aE != 0 ? a.label + this.htmlcA + a.aE + this.htmlcB: a.label;
		break;
	case 2:
		var b = this._getLeafCount(a);
		a.span.innerHTML = b != 0 ? a.label + this.htmlcA + b + this.htmlcB: a.label;
		break;
	case 3:
		if (a.aE != 0) {
			for (var c = 0,
			d = 0; d < a.aE; d++) {
				if (!a.childNodes[d]._acc) a.childNodes[d]._acc = 0;
				c += a.childNodes[d]._acc * 1
			}
			c += a.aE * 1;
			a.span.innerHTML = a.label + this.htmlcA + c + this.htmlcB;
			a._acc = c
		} else a.span.innerHTML = a.label,
		a._acc = 0;
		a.parentObject && a.parentObject != this.ao && this._fixChildCountLabel(a.parentObject);
		break;
	case 4:
		if (a.aE != 0) {
			for (d = c = 0; d < a.aE; d++) {
				if (!a.childNodes[d]._acc) a.childNodes[d]._acc = 1;
				c += a.childNodes[d]._acc * 1
			}
			a.span.innerHTML = a.label + this.htmlcA + c + this.htmlcB;
			a._acc = c
		} else a.span.innerHTML = a.label,
		a._acc = 1;
		a.parentObject && a.parentObject != this.ao && this._fixChildCountLabel(a.parentObject)
	}
};
dhtmlXTreeObject.prototype.setChildCalcMode = function(a) {
	switch (a) {
	case "child":
		this.childCalc = 1;
		break;
	case "leafs":
		this.childCalc = 2;
		break;
	case "childrec":
		this.childCalc = 3;
		break;
	case "leafsrec":
		this.childCalc = 4;
		break;
	case "disabled":
		this.childCalc = null;
		break;
	default:
		this.childCalc = 4
	}
};
dhtmlXTreeObject.prototype.setChildCalcHTML = function(a, b) {
	this.htmlcA = a;
	this.htmlcB = b
};
dhtmlXTreeObject.prototype.setOnRightClickHandler = function(a) {
	this.attachEvent("onRightClick", a)
};
dhtmlXTreeObject.prototype.ck = function(a) {
	this.attachEvent("onClick", a)
};
dhtmlXTreeObject.prototype.setOnSelectStateChange = function(a) {
	this.attachEvent("onSelect", a)
};
dhtmlXTreeObject.prototype.eG = function(a) {
	this.XMLsource = a
};
dhtmlXTreeObject.prototype.setOnCheckHandler = function(a) {
	this.attachEvent("onCheck", a)
};
dhtmlXTreeObject.prototype.setOnOpenHandler = function(a) {
	this.attachEvent("onOpenStart", a)
};
dhtmlXTreeObject.prototype.setOnOpenStartHandler = function(a) {
	this.attachEvent("onOpenStart", a)
};
dhtmlXTreeObject.prototype.setOnOpenEndHandler = function(a) {
	this.attachEvent("onOpenEnd", a)
};
dhtmlXTreeObject.prototype.setOnDblClickHandler = function(a) {
	this.attachEvent("onDblClick", a)
};
dhtmlXTreeObject.prototype.Jo = function(a) {
	var b = this.ak(a);
	if (!b) return 0;
	this._xopenAll(b)
};
dhtmlXTreeObject.prototype.getOpenState = function(a) {
	var b = this.ak(a);
	return ! b ? "": this._getOpenState(b)
};
dhtmlXTreeObject.prototype.closeAllItems = function(a) {
	if (a === window.undefined) a = this.fO;
	var b = this.ak(a);
	if (!b) return 0;
	this._xcloseAll(b);
	this.cB.childNodes[0].border = "1";
	this.cB.childNodes[0].border = "0"
};
dhtmlXTreeObject.prototype.setUserData = function(a, b, c) {
	var d = this.ak(a, 0, !0);
	if (d) {
		if (b == "hint") d.ao.childNodes[0].childNodes[0].title = c;
		if (typeof d.userData["t_" + b] == "undefined") d.PJ ? d.PJ += "," + b: d.PJ = b;
		d.userData["t_" + b] = c
	}
};
dhtmlXTreeObject.prototype.getUserData = function(a, b) {
	var c = this.ak(a, 0, !0);
	return ! c ? void 0 : c.userData["t_" + b]
};
dhtmlXTreeObject.prototype.getItemColor = function(a) {
	var b = this.ak(a);
	if (!b) return 0;
	var c = {};
	if (b.acolor) c.acolor = b.acolor;
	if (b.mB) c.mB = b.mB;
	return c
};
dhtmlXTreeObject.prototype.setItemColor = function(a, b, c) {
	var d = a && a.span ? a: this.ak(a);
	if (d) {
		if (d.i_sel) {
			if (c) d.span.style.color = c
		} else if (b) d.span.style.color = b;
		if (c) d.mB = c;
		if (b) d.acolor = b
	} else return 0
};
dhtmlXTreeObject.prototype.getItemText = function(a) {
	var b = this.ak(a);
	return ! b ? 0 : b.ao.childNodes[0].childNodes[0].childNodes[3].childNodes[0].innerHTML
};
dhtmlXTreeObject.prototype.getParentId = function(a) {
	var b = this.ak(a);
	return ! b || !b.parentObject ? "": b.parentObject.id
};
dhtmlXTreeObject.prototype.changeItemId = function(a, b) {
	if (a != b) {
		var c = this.ak(a);
		if (!c) return 0;
		c.id = b;
		c.span.contextMenuId = b;
		this.gQ[b] = this.gQ[a];
		delete this.gQ[a]
	}
};
dhtmlXTreeObject.prototype.doCut = function() {
	this.nodeCut && this.clearCut();
	this.nodeCut = [].concat(this._selected);
	for (var a = 0; a < this.nodeCut.length; a++) {
		var b = this.nodeCut[a];
		b._cimgs = [];
		b._cimgs[0] = b.images[0];
		b._cimgs[1] = b.images[1];
		b._cimgs[2] = b.images[2];
		b.images[0] = b.images[1] = b.images[2] = this.cutImage;
		this._correctPlus(b)
	}
};
dhtmlXTreeObject.prototype.doPaste = function(a) {
	var b = this.ak(a);
	if (!b) return 0;
	for (var c = 0; c < this.nodeCut.length; c++) this._checkPNodes(b, this.nodeCut[c]) || this._moveNode(this.nodeCut[c], b);
	this.clearCut()
};
dhtmlXTreeObject.prototype.clearCut = function() {
	for (var a = 0; a < this.nodeCut.length; a++) {
		var b = this.nodeCut[a];
		b.images[0] = b._cimgs[0];
		b.images[1] = b._cimgs[1];
		b.images[2] = b._cimgs[2];
		this._correctPlus(b)
	}
	this.nodeCut = []
};
dhtmlXTreeObject.prototype._moveNode = function(a, b) {
	var c = this.dadmodec;
	if (c == 1) {
		var d = b;
		if (this.dadmodefix < 0) {
			for (;;) {
				d = this._getPrevNode(d);
				if (d == -1) {
					d = this.ao;
					break
				}
				if (d.tr == 0 || d.tr.style.display == "" || !d.parentObject) break
			}
			var e = d,
			f = b
		} else {
			if (d.tr && d.tr.nextSibling && d.tr.nextSibling.nodem && !this._getOpenState(d)) d = d.tr.nextSibling.nodem;
			else if (d = this._getNextNode(d), d == -1) d = this.ao;
			f = d;
			e = b
		}
		return this._getNodeLevel(e, 0) > this._getNodeLevel(f, 0) ? this.dropLower ? f.id != this.fO ? this._moveNodeTo(a, f.parentObject, f) : this._moveNodeTo(a, this.ao, null) : this._moveNodeTo(a, e.parentObject) : this._moveNodeTo(a, f.parentObject, f)
	} else return this._moveNodeTo(a, b)
};
dhtmlXTreeObject.prototype._fixNodesCollection = function(a, b) {
	var c = 0,
	d = 0,
	e = a.childNodes,
	f = a.aE - 1;
	if (b != e[f]) {
		for (var g = 0; g < f; g++) e[g] == e[f] && (e[g] = e[g + 1], e[g + 1] = e[f]);
		for (g = 0; g < f + 1; g++) if (c) {
			var h = e[g];
			e[g] = c;
			c = h
		} else e[g] == b && (c = e[g], e[g] = e[f])
	}
};
dhtmlXTreeObject.prototype._recreateBranch = function(a, b, c, d) {
	var e, f = "";
	if (c) {
		for (e = 0; e < b.aE; e++) if (b.childNodes[e] == c) break;
		e != 0 ? c = b.childNodes[e - 1] : (f = "TOP", c = "")
	}
	var g = this.BX;
	this.BX = null;
	var h = this._attachChildNode(b, a.id, a.label, 0, a.images[0], a.images[1], a.images[2], f, 0, c);
	h.PJ = a.PJ;
	h.userData = a.userData.clone();
	if (a._attrs) {
		h._attrs = {};
		for (var j in a._attrs) h._attrs[j] = a._attrs[j]
	}
	h.XMLload = a.XMLload;
	if (g) this.BX = g,
	this.BX(h.id);
	a.treeNod.dpcpy ? a.treeNod.ak(a.id) : h.unParsed = a.unParsed;
	this._correctPlus(h);
	for (e = 0; e < a.aE; e++) this._recreateBranch(a.childNodes[e], h, 0, 1); ! d && this.childCalc && this._redrawFrom(this, b);
	return h
};
dhtmlXTreeObject.prototype._moveNodeTo = function(a, b, c) {
	if (a.treeNod._nonTrivialNode) return a.treeNod._nonTrivialNode(this, b, c, a);
	if (this._checkPNodes(b, a)) return ! 1;
	var d = b.acE ? a.treeNod.lWin != b.lWin: a.treeNod.lWin != b.treeNod.lWin;
	if (!this.callEvent("onDrag", [a.id, b.id, c ? c.id: null, a.treeNod, b.treeNod])) return ! 1;
	if (b.XMLload == 0 && this.XMLsource) b.XMLload = 1,
	this._loadDynXML(b.id);
	this.hx(b.id);
	var e = a.treeNod,
	f = a.parentObject.aE,
	g = a.parentObject;
	if (d || e.dpcpy) {
		var h = a.id,
		a = this._recreateBranch(a, b, c);
		e.dpcpy || e.deleteItem(h)
	} else {
		var j = b.aE,
		i = b.childNodes;
		if (j == 0) b._open = !0;
		e._unselectItem(a);
		i[j] = a;
		a.treeNod = b.treeNod;
		b.aE++;
		var m = this._drawNewTr(i[j].ao);
		c ? (b.ao.childNodes[0].insertBefore(m, c.tr), this._fixNodesCollection(b, c), i = b.childNodes) : (b.ao.childNodes[0].appendChild(m), this.dadmode == 1 && this._fixNodesCollection(b, c))
	}
	if (!e.dpcpy && !d) {
		var l = a.tr;
		document.all && navigator.appVersion.search(/MSIE\ 5\.0/gi) != -1 ? window.setTimeout(function() {
			l.parentNode.removeChild(l)
		},
		250) : a.parentObject.ao.childNodes[0].removeChild(a.tr);
		if (!c || b != a.parentObject) for (var k = 0; k < g.aE; k++) {
			if (g.childNodes[k].id == a.id) {
				g.childNodes[k] = 0;
				break
			}
		} else g.childNodes[g.aE - 1] = 0;
		e._compressChildList(g.aE, g.childNodes);
		g.aE--
	}
	if (!d && !e.dpcpy) {
		a.tr = m;
		m.nodem = a;
		a.parentObject = b;
		if (e != b.treeNod) {
			if (a.treeNod._registerBranch(a, e)) return;
			this._clearStyles(a);
			this._redrawFrom(this, a.parentObject);
			this.BX && this.BX(a.id)
		}
		this._correctPlus(b);
		this._correctLine(b);
		this._correctLine(a);
		this._correctPlus(a);
		c ? this._correctPlus(c) : b.aE >= 2 && (this._correctPlus(i[b.aE - 2]), this._correctLine(i[b.aE - 2]));
		this._correctPlus(i[b.aE - 1]);
		this.tscheck && this._correctCheckStates(b);
		e.tscheck && e._correctCheckStates(g)
	}
	f > 1 && (e._correctPlus(g.childNodes[f - 2]), e._correctLine(g.childNodes[f - 2]));
	e._correctPlus(g);
	e._correctLine(g);
	this._fixChildCountLabel(b);
	e._fixChildCountLabel(g);
	this.callEvent("onDrop", [a.id, b.id, c ? c.id: null, e, b.treeNod]);
	return a.id
};
dhtmlXTreeObject.prototype._clearStyles = function(a) {
	if (a.ao) {
		var b = a.ao.childNodes[0].childNodes[0].childNodes[1],
		c = b.nextSibling.nextSibling;
		a.span.innerHTML = a.label;
		a.i_sel = !1;
		a._aimgs && this.ae.fG(b.nextSibling);
		this.checkBoxOff ? (b.childNodes[0].style.display = "", b.childNodes[0].onclick = this.onCheckBoxClick, this._setSrc(b.childNodes[0], this.imPath + this.checkArray[a.checkstate])) : b.childNodes[0].style.display = "none";
		b.childNodes[0].treeNod = this;
		this.ae.fG(c);
		this.nz && this.ae.dS(c, this);
		this._aimgs && this.ae.dS(b.nextSibling, this);
		c.childNodes[0].className = "standartTreeRow";
		c.onclick = this.onRowSelect;
		c.ondblclick = this.onRowClick2;
		b.previousSibling.onclick = this.onRowClick;
		this._correctLine(a);
		this._correctPlus(a);
		for (var d = 0; d < a.aE; d++) this._clearStyles(a.childNodes[d])
	}
};
dhtmlXTreeObject.prototype._registerBranch = function(a, b) {
	b && b._globalIdStorageSub(a.id);
	a.id = this._globalIdStorageAdd(a.id, a);
	a.treeNod = this;
	for (var c = 0; c < a.aE; c++) this._registerBranch(a.childNodes[c], b);
	return 0
};
dhtmlXTreeObject.prototype.enableThreeStateCheckboxes = function(a) {
	this.tscheck = ap(a)
};
dhtmlXTreeObject.prototype.setOnMouseInHandler = function(a) {
	this.ehlt = !0;
	this.attachEvent("onMouseIn", a)
};
dhtmlXTreeObject.prototype.setOnMouseOutHandler = function(a) {
	this.ehlt = !0;
	this.attachEvent("onMouseOut", a)
};
dhtmlXTreeObject.prototype.enableMercyDrag = function(a) {
	this.dpcpy = ap(a)
};
dhtmlXTreeObject.prototype.enableTreeImages = function(a) {
	this.timgen = ap(a)
};
dhtmlXTreeObject.prototype.enableFixedMode = function(a) {
	this.hfMode = ap(a)
};
dhtmlXTreeObject.prototype.enableCheckBoxes = function(a, b) {
	this.checkBoxOff = ap(a);
	this.cBROf = !(this.checkBoxOff || ap(b))
};
dhtmlXTreeObject.prototype.setStdImages = function(a, b, c) {
	this.imageArray[0] = a;
	this.imageArray[1] = b;
	this.imageArray[2] = c
};
dhtmlXTreeObject.prototype.enableTreeLines = function(a) {
	this.treeLinesOn = ap(a)
};
dhtmlXTreeObject.prototype.setImageArrays = function(a, b, c, d, e, f) {
	switch (a) {
	case "plus":
		this.plusArray[0] = b;
		this.plusArray[1] = c;
		this.plusArray[2] = d;
		this.plusArray[3] = e;
		this.plusArray[4] = f;
		break;
	case "minus":
		this.minusArray[0] = b,
		this.minusArray[1] = c,
		this.minusArray[2] = d,
		this.minusArray[3] = e,
		this.minusArray[4] = f
	}
};
dhtmlXTreeObject.prototype.hx = function(a) {
	var b = this.ak(a);
	return b ? this._openItem(b) : 0
};
dhtmlXTreeObject.prototype._openItem = function(a) {
	var b = this._getOpenState(a);
	if (b < 0 || this.XMLsource && !a.XMLload) {
		if (!this.callEvent("onOpenStart", [a.id, b])) return 0;
		this._HideShow(a, 2);
		if (this.mR("onOpenEnd")) this.onXLE == this._epnFHe && this._epnFHe(this, a.id, !0),
		!this.xmlstate || !this.XMLsource ? this.callEvent("onOpenEnd", [a.id, this._getOpenState(a)]) : (this._oie_onXLE.push(this.onXLE), this.onXLE = this._epnFHe)
	} else this.ahK && this._HideShow(a, 2);
	a.parentObject && !this._skip_open_parent && this._openItem(a.parentObject)
};
dhtmlXTreeObject.prototype.KW = function(a) {
	if (this.fO == a) return 0;
	var b = this.ak(a);
	if (!b) return 0;
	b.closeble && this._HideShow(b, 1)
};
dhtmlXTreeObject.prototype.sS = function(a) {
	var b = this.ak(a);
	return ! b ? 0 : this._getNodeLevel(b, 0)
};
dhtmlXTreeObject.prototype.setItemCloseable = function(a, b) {
	var b = ap(b),
	c = a && a.span ? a: this.ak(a);
	if (!c) return 0;
	c.closeble = b
};
dhtmlXTreeObject.prototype._getNodeLevel = function(a, b) {
	return a.parentObject ? this._getNodeLevel(a.parentObject, b + 1) : b
};
dhtmlXTreeObject.prototype.hasChildren = function(a) {
	var b = this.ak(a);
	return b ? this.XMLsource && !b.XMLload ? !0 : b.aE: 0
};
dhtmlXTreeObject.prototype._getLeafCount = function(a) {
	for (var b = 0,
	c = 0; c < a.aE; c++) a.childNodes[c].aE == 0 && b++;
	return b
};
dhtmlXTreeObject.prototype.setItemText = function(a, b, c) {
	var d = this.ak(a);
	if (!d) return 0;
	d.label = b;
	d.span.innerHTML = b;
	this.childCalc && this._fixChildCountLabel(d);
	d.span.parentNode.parentNode.title = c || ""
};
dhtmlXTreeObject.prototype.getItemTooltip = function(a) {
	var b = this.ak(a);
	return ! b ? "": b.span.parentNode.parentNode._dhx_title || b.span.parentNode.parentNode.title || ""
};
dhtmlXTreeObject.prototype.refreshItem = function(a) {
	if (!a) a = this.fO;
	var b = this.ak(a);
	this.cx(a);
	this._loadDynXML(a)
};
dhtmlXTreeObject.prototype.dC = function(a, b, c, d) {
	var e = this.ak(a);
	if (!e) return 0;
	e.images[1] = c;
	e.images[2] = d;
	e.images[0] = b;
	this._correctPlus(e)
};
dhtmlXTreeObject.prototype.setItemImage = function(a, b, c) {
	var d = this.ak(a);
	if (!d) return 0;
	c ? (d.images[1] = b, d.images[2] = c) : d.images[0] = b;
	this._correctPlus(d)
};
dhtmlXTreeObject.prototype.Mn = function(a) {
	var b = this.ak(a, 0, 1);
	if (!b) return 0;
	if (b.unParsed) return this._getSubItemsXML(b.unParsed);
	var c = "";
	for (i = 0; i < b.aE; i++) c ? c += this.dlmtr + b.childNodes[i].id: c = "" + b.childNodes[i].id;
	return c
};
dhtmlXTreeObject.prototype._getAllScraggyItems = function(a) {
	for (var b = "",
	c = 0; c < a.aE; c++) if (a.childNodes[c].unParsed || a.childNodes[c].aE > 0) {
		var d = a.childNodes[c].unParsed ? this._getAllScraggyItemsXML(a.childNodes[c].unParsed, 1) : this._getAllScraggyItems(a.childNodes[c]);
		d && (b ? b += this.dlmtr + d: b = d)
	} else b ? b += this.dlmtr + a.childNodes[c].id: b = "" + a.childNodes[c].id;
	return b
};
dhtmlXTreeObject.prototype._getAllFatItems = function(a) {
	for (var b = "",
	c = 0; c < a.aE; c++) if (a.childNodes[c].unParsed || a.childNodes[c].aE > 0) {
		b ? b += this.dlmtr + a.childNodes[c].id: b = "" + a.childNodes[c].id;
		var d = a.childNodes[c].unParsed ? this._getAllFatItemsXML(a.childNodes[c].unParsed, 1) : this._getAllFatItems(a.childNodes[c]);
		d && (b += this.dlmtr + d)
	}
	return b
};
dhtmlXTreeObject.prototype._getAllSubItems = function(a, b, c) {
	var d = c ? c: this.ak(a);
	if (!d) return 0;
	for (var b = "",
	e = 0; e < d.aE; e++) {
		b ? b += this.dlmtr + d.childNodes[e].id: b = "" + d.childNodes[e].id;
		var f = this._getAllSubItems(0, b, d.childNodes[e]);
		f && (b += this.dlmtr + f)
	}
	d.unParsed && (b = this._getAllSubItemsXML(a, b, d.unParsed));
	return b
};
dhtmlXTreeObject.prototype.hE = function(a, b, c) {
	var b = ap(b),
	d = this.ak(a);
	if (!d || !d.parentObject) return 0;
	this.XMLloadingWarning ? d.parentObject.openMe = 1 : this._openItem(d.parentObject);
	var e = null;
	if (c && (e = {
		ctrlKey: !0
	},
	d.i_sel)) e.skipUnSel = !0;
	if (b) this.onRowSelect(e, d.ao.childNodes[0].childNodes[0].childNodes[3], !1);
	else this.onRowSelect(e, d.ao.childNodes[0].childNodes[0].childNodes[3], !0)
};
dhtmlXTreeObject.prototype.getSelectedItemText = function() {
	for (var a = [], b = 0; b < this._selected.length; b++) a[b] = this._selected[b].span.innerHTML;
	return a.join(this.dlmtr)
};
dhtmlXTreeObject.prototype._compressChildList = function(a, b) {
	a--;
	for (var c = 0; c < a; c++) b[c] == 0 && (b[c] = b[c + 1], b[c + 1] = 0)
};
dhtmlXTreeObject.prototype._deleteNode = function(a, b, c) {
	if (!b || !b.parentObject) return 0;
	var d = 0,
	e = 0;
	if (b.tr.nextSibling) d = b.tr.nextSibling.nodem;
	if (b.tr.previousSibling) e = b.tr.previousSibling.nodem;
	for (var f = b.parentObject,
	g = f.aE,
	h = f.childNodes,
	j = 0; j < g; j++) if (h[j].id == a) {
		c || f.ao.childNodes[0].removeChild(h[j].tr);
		h[j] = 0;
		break
	}
	this._compressChildList(g, h);
	c || f.aE--;
	d && (this._correctPlus(d), this._correctLine(d));
	e && (this._correctPlus(e), this._correctLine(e));
	this.tscheck && this._correctCheckStates(f);
	c || this._globalIdStorageRecSub(b)
};
dhtmlXTreeObject.prototype.setCheck = function(a, b) {
	var c = this.ak(a, 0, 1);
	c && (b === "unsure" ? this._setCheck(c, b) : (b = ap(b), this.tscheck && this.smcheck ? this._setSubChecked(b, c) : this._setCheck(c, b)), this.smcheck && this._correctCheckStates(c.parentObject))
};
dhtmlXTreeObject.prototype._setCheck = function(a, b) {
	if (a) {
		if ((a.parentObject._r_logic || this._frbtr) && b) if (this._frbtrs) this._frbtrL && this.setCheck(this._frbtrL.id, 0),
		this._frbtrL = a;
		else for (var c = 0; c < a.parentObject.aE; c++) this._setCheck(a.parentObject.childNodes[c], 0);
		var d = a.ao.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
		a.checkstate = b == "unsure" ? 2 : b ? 1 : 0;
		if (a.dscheck) a.checkstate = a.dscheck;
		this._setSrc(d, this.imPath + (a.parentObject._r_logic || this._frbtr ? this.radioArray: this.checkArray)[a.checkstate])
	}
};
dhtmlXTreeObject.prototype.setSubChecked = function(a, b) {
	var c = this.ak(a);
	this._setSubChecked(b, c);
	this._correctCheckStates(c.parentObject)
};
dhtmlXTreeObject.prototype._setSubChecked = function(a, b) {
	a = ap(a);
	if (b) {
		if ((b.parentObject._r_logic || this._frbtr) && a) for (var c = 0; c < b.parentObject.aE; c++) this._setSubChecked(0, b.parentObject.childNodes[c]);
		b.unParsed && this._setSubCheckedXML(a, b.unParsed);
		if (b._r_logic || this._frbtr) this._setSubChecked(a, b.childNodes[0]);
		else for (c = 0; c < b.aE; c++) this._setSubChecked(a, b.childNodes[c]);
		var d = b.ao.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
		b.checkstate = a ? 1 : 0;
		if (b.dscheck) b.checkstate = b.dscheck;
		this._setSrc(d, this.imPath + (b.parentObject._r_logic || this._frbtr ? this.radioArray: this.checkArray)[b.checkstate])
	}
};
dhtmlXTreeObject.prototype.ajT = function(a) {
	var b = this.ak(a);
	return ! b ? void 0 : b.checkstate
};
dhtmlXTreeObject.prototype.cx = function(a) {
	var b = this.ak(a);
	if (b) for (var c = b.aE,
	d = 0; d < c; d++) this._deleteNode(b.childNodes[0].id, b.childNodes[0])
};
dhtmlXTreeObject.prototype.deleteChildItems = function(itemId) {
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) return;
	var j = sNode.childsCount;
	for (var i = 0; i < j; i++) {
		this._deleteNode(sNode.childNodes[0].id, sNode.childNodes[0]);
	};
};
dhtmlXTreeObject.prototype.deleteItem = function(a, b) {
	if (!this.ajc || this.ajc(a)) {
		var c = this._deleteItem(a, b);
		c && this._fixChildCountLabel(c)
	}
	this.cB.childNodes[0].border = "1";
	this.cB.childNodes[0].border = "0"
};
dhtmlXTreeObject.prototype._deleteItem = function(a, b, c) {
	var b = ap(b),
	d = this.ak(a);
	if (d) {
		var e = this.getParentId(a),
		f = d.parentObject;
		this._deleteNode(a, d, c);
		if (this._editCell && this._editCell.id == a) this._editCell = null;
		this._correctPlus(f);
		this._correctLine(f);
		b && e != this.fO && this.hE(e, 1);
		return f
	}
};
dhtmlXTreeObject.prototype._globalIdStorageRecSub = function(a) {
	for (var b = 0; b < a.aE; b++) this._globalIdStorageRecSub(a.childNodes[b]),
	this._globalIdStorageSub(a.childNodes[b].id);
	this._globalIdStorageSub(a.id);
	var c = a;
	c.span = null;
	c.tr.nodem = null;
	c.tr = null;
	c.ao = null
};
dhtmlXTreeObject.prototype.insertNewNext = function(a, b, c, d, e, f, g, h, j) {
	var i = this.ak(a);
	if (!i || !i.parentObject) return 0;
	var m = this._attachChildNode(0, b, c, d, e, f, g, h, j, i); ! this.XMLloadingWarning && this.childCalc && this._fixChildCountLabel(i.parentObject);
	return m
};
dhtmlXTreeObject.prototype.afD = function(a, b) {
	var c = this.ak(a);
	return ! c || b >= c.aE ? null: c.childNodes[b].id
};
dhtmlXTreeObject.prototype.getChildItemIdByIndex = function(a, b) {
	var c = this.ak(a);
	return ! c || b >= c.aE ? null: c.childNodes[b].id
};
dhtmlXTreeObject.prototype.setDragHandler = function(a) {
	this.attachEvent("onDrag", a)
};
dhtmlXTreeObject.prototype._clearMove = function() {
	if (this._lastMark) this._lastMark.className = this._lastMark.className.replace(/dragAndDropRow/g, ""),
	this._lastMark = null;
	this.selectionBar.style.display = "none";
	this.cB.className = this.cB.className.replace(" selectionBox", "")
};
dhtmlXTreeObject.prototype.enableDragAndDrop = function(a, b) {
	a == "temporary_disabled" ? (this.dADTempOff = !1, a = !0) : this.dADTempOff = !0; (this.nz = ap(a)) && this.ae.eH(this.cB, this);
	if (arguments.length > 1) this._ddronr = !ap(b)
};
dhtmlXTreeObject.prototype._setMove = function(a, b, c) {
	if (a.parentObject.span) {
		var d = dg(a),
		e = dg(this.cB) - this.cB.scrollTop;
		this.dadmodec = this.dadmode;
		this.dadmodefix = 0;
		if (this.dadmode == 2) {
			var f = c - d + (document.body.scrollTop || document.documentElement.scrollTop) - 2 - a.offsetHeight / 2;
			if (Math.abs(f) - a.offsetHeight / 6 > 0) {
				if (this.dadmodec = 1, f < 0) this.dadmodefix = 0 - a.offsetHeight
			} else this.dadmodec = 0
		}
		if (this.dadmodec == 0) {
			var g = a.parentObject.span;
			g.className += " dragAndDropRow";
			this._lastMark = g
		} else {
			this._clearMove();
			this.selectionBar.style.top = d - e + ((parseInt(a.parentObject.span.parentNode.previousSibling.childNodes[0].style.height) || 18) - 1) + this.dadmodefix + "px";
			this.selectionBar.style.left = "5px";
			if (this.cB.offsetWidth > 20) this.selectionBar.style.width = this.cB.offsetWidth - (aq ? 30 : 25) + "px";
			this.selectionBar.style.display = ""
		}
		this._autoScroll(null, d, e)
	}
};
dhtmlXTreeObject.prototype._autoScroll = function(a, b, c) {
	if (this.autoScroll) {
		a && (b = dg(a), c = dg(this.cB) - this.cB.scrollTop);
		if (b - c - parseInt(this.cB.scrollTop) > parseInt(this.cB.offsetHeight) - 50) this.cB.scrollTop = parseInt(this.cB.scrollTop) + 20;
		if (b - c < parseInt(this.cB.scrollTop) + 30) this.cB.scrollTop = parseInt(this.cB.scrollTop) - 20
	}
};
dhtmlXTreeObject.prototype.gL = function(a, b) {
	if (!this.dADTempOff) return null;
	var c = a.parentObject;
	if (!this.callEvent("onBeforeDrag", [c.id, b])) return null;
	c.i_sel || this._selectItem(c, b);
	this._checkMSelectionLogic();
	var d = document.createElement("div"),
	e = [];
	if (this._itim_dg) for (var f = 0; f < this._selected.length; f++) e[f] = "<table cellspacing='0' cellpadding='0'><tr><td><img width='18px' height='18px' src='" + this._getSrc(this._selected[f].span.parentNode.previousSibling.childNodes[0]) + "'></td><td>" + this._selected[f].span.innerHTML + "</td></tr></table>";
	else e = this.getSelectedItemText().split(this.dlmtr);
	d.innerHTML = e.join("");
	d.style.position = "absolute";
	d.className = "dragSpanDiv";
	this.jG = [].concat(this._selected);
	return d
};
dhtmlXTreeObject.prototype._focusNode = function(a) {
	var b = dg(a.ao) - dg(this.cB);
	if (b > this.cB.offsetHeight - 30 || b < 0) this.cB.scrollTop = b + this.cB.scrollTop
};
dhtmlXTreeObject.prototype._preventNsDrag = function(a) {
	a && a.preventDefault && a.preventDefault();
	return ! 1
};
dhtmlXTreeObject.prototype.BK = function(a, b, c) {
	this._autoOpenTimer && clearTimeout(this._autoOpenTimer);
	if (!c.parentObject) c = this.ao.ao.childNodes[0].childNodes[0].childNodes[1].childNodes[0],
	this.dadmodec = 0;
	this._clearMove();
	var d = a.parentObject.treeNod;
	d && d._clearMove && d._clearMove("");
	if (!this.dragMove || this.dragMove()) for (var e = !d || !d._clearMove || !d.jG ? Array(a.parentObject) : d.jG, f = c.parentObject, g = 0; g < e.length; g++) {
		var h = this._moveNode(e[g], f);
		this.dadmodec && h !== !1 && (f = this.ak(h, !0, !0));
		h && !this._sADnD && this.hE(h, 0, 1)
	}
	if (d) d.jG = []
};
dhtmlXTreeObject.prototype.tY = function(a, b, c, d) {
	if (!this.dADTempOff) return 0;
	var e = b.parentObject,
	f = a.parentObject;
	if (f || !this._ddronr) {
		if (!this.callEvent("onDragIn", [e.id, f ? f.id: null, e.treeNod, this])) return f && this._autoScroll(a),
		0;
		if (f) {
			if (e.childNodes == null) return this._setMove(a, c, d),
			a;
			for (var g = e.treeNod,
			h = 0; h < g.jG.length; h++) if (this._checkPNodes(f, g.jG[h])) return this._autoScroll(a),
			0;
			this.selectionBar.parentNode.removeChild(this.selectionBar);
			f.span.parentNode.appendChild(this.selectionBar);
			this._setMove(a, c, d);
			if (this._getOpenState(f) <= 0) this._autoOpenId = f.id,
			this._autoOpenTimer = window.setTimeout(new jP(this._autoOpenItem, this), 1E3)
		} else this.cB.className += " selectionBox";
		return a
	}
};
dhtmlXTreeObject.prototype._autoOpenItem = function(a, b) {
	b.hx(b._autoOpenId)
};
dhtmlXTreeObject.prototype.iX = function() {
	this._clearMove();
	this._autoOpenTimer && clearTimeout(this._autoOpenTimer)
};
dhtmlXTreeObject.prototype._getNextNode = function(a, b) {
	return ! b && a.aE ? a.childNodes[0] : a == this.ao ? -1 : a.tr && a.tr.nextSibling && a.tr.nextSibling.nodem ? a.tr.nextSibling.nodem: this._getNextNode(a.parentObject, !0)
};
dhtmlXTreeObject.prototype._lastChild = function(a) {
	return a.aE ? this._lastChild(a.childNodes[a.aE - 1]) : a
};
dhtmlXTreeObject.prototype._getPrevNode = function(a) {
	return a.tr && a.tr.previousSibling && a.tr.previousSibling.nodem ? this._lastChild(a.tr.previousSibling.nodem) : a.parentObject ? a.parentObject: -1
};
dhtmlXTreeObject.prototype.findItem = function(a, b, c) {
	var d = this._findNodeByLabel(a, b, c ? this.ao: null);
	return d ? (this.hE(d.id, !0), this._focusNode(d), d.id) : null
};
dhtmlXTreeObject.prototype.findItemIdByLabel = function(a, b, c) {
	var d = this._findNodeByLabel(a, b, c ? this.ao: null);
	return d ? d.id: null
};
dhtmlXTreeObject.prototype.findStrInXML = function(a, b, c) {
	if (!a.childNodes && a.item) return this.findStrInJSON(a, b, c);
	if (!a.childNodes) return ! 1;
	for (var d = 0; d < a.childNodes.length; d++) if (a.childNodes[d].nodeType == 1) {
		var e = a.childNodes[d].getAttribute(b);
		if (!e && a.childNodes[d].tagName == "itemtext") e = a.childNodes[d].firstChild.data;
		if (e && e.toLowerCase().search(c) != -1) return ! 0;
		if (this.findStrInXML(a.childNodes[d], b, c)) return ! 0
	}
	return ! 1
};
dhtmlXTreeObject.prototype.findStrInJSON = function(a, b, c) {
	for (var d = 0; d < a.item.length; d++) {
		var e = a.item[d].text;
		if (e && e.toLowerCase().search(c) != -1) return ! 0;
		if (a.item[d].item && this.findStrInJSON(a.item[d], b, c)) return ! 0
	}
	return ! 1
};
dhtmlXTreeObject.prototype._findNodeByLabel = function(a, b, c) {
	a = a.replace(/^( )+/, "").replace(/( )+$/, "");
	a = RegExp(a.replace(/([\?\*\+\\\[\]\(\)]{1})/gi, "\\$1").replace(/ /gi, ".*"), "gi");
	if (!c && (c = this._selected[0], !c)) c = this.ao;
	var d = c;
	if (b) {
		var e = this._getPrevNode(d);
		e == -1 && (e = this._lastChild(this.ao));
		e.unParsed && this.findStrInXML(e.unParsed.d, "text", a) ? (this.reParse(e), c = this._getPrevNode(d)) : c = e;
		c == -1 && (c = this._lastChild(this.ao))
	} else c.unParsed && this.findStrInXML(c.unParsed.d, "text", a) && this.reParse(c),
	c = this._getNextNode(d),
	c == -1 && (c = this.ao.childNodes[0]);
	for (; c && c != d;) {
		if (c.label && c.label.search(a) != -1) return c;
		if (b) e = this._getPrevNode(c),
		e == -1 && (e = this._lastChild(this.ao)),
		e.unParsed && this.findStrInXML(e.unParsed.d, "text", a) ? (this.reParse(e), c = this._getPrevNode(c)) : c = e,
		c == -1 && (c = this._lastChild(this.ao));
		else {
			if (c == -1) {
				if (d == this.ao) break;
				c = this.ao.childNodes[0]
			}
			c.unParsed && this.findStrInXML(c.unParsed.d, "text", a) && this.reParse(c);
			c = this._getNextNode(c);
			if (c == -1) c = this.ao
		}
	}
	return null
};
dhtmlXTreeObject.prototype.moveItem = function(a, b, c, d) {
	var e = this.ak(a);
	if (!e) return 0;
	switch (b) {
	case "right":
		alert("Not supported yet");
		break;
	case "item_child":
		var f = (d || this).ak(c);
		if (!f) return 0; (d || this)._moveNodeTo(e, f, 0);
		break;
	case "item_sibling":
		f = (d || this).ak(c);
		if (!f) return 0; (d || this)._moveNodeTo(e, f.parentObject, f);
		break;
	case "item_sibling_next":
		f = (d || this).ak(c);
		if (!f) return 0;
		f.tr && f.tr.nextSibling && f.tr.nextSibling.nodem ? (d || this)._moveNodeTo(e, f.parentObject, f.tr.nextSibling.nodem) : (d || this)._moveNodeTo(e, f.parentObject);
		break;
	case "left":
		e.parentObject.parentObject && this._moveNodeTo(e, e.parentObject.parentObject, e.parentObject);
		break;
	case "up":
		var g = this._getPrevNode(e);
		if (g == -1 || !g.parentObject) return;
		this._moveNodeTo(e, g.parentObject, g);
		break;
	case "up_strict":
		g = this._getIndex(e);
		g != 0 && this._moveNodeTo(e, e.parentObject, e.parentObject.childNodes[g - 1]);
		break;
	case "down_strict":
		var g = this._getIndex(e),
		h = e.parentObject.aE - 2;
		g == h ? this._moveNodeTo(e, e.parentObject) : g < h && this._moveNodeTo(e, e.parentObject, e.parentObject.childNodes[g + 2]);
		break;
	case "down":
		g = this._getNextNode(this._lastChild(e));
		if (g == -1 || !g.parentObject) return;
		g.parentObject == e.parentObject && (g = this._getNextNode(g));
		if (g == -1) this._moveNodeTo(e, e.parentObject);
		else {
			if (g == -1 || !g.parentObject) return;
			this._moveNodeTo(e, g.parentObject, g)
		}
	}
	if (_isIE && _isIE < 8) this.cB.childNodes[0].border = "1",
	this.cB.childNodes[0].border = "0"
};
dhtmlXTreeObject.prototype.setDragBehavior = function(a, b) {
	this._sADnD = !ap(b);
	switch (a) {
	case "child":
		this.dadmode = 0;
		break;
	case "sibling":
		this.dadmode = 1;
		break;
	case "complex":
		this.dadmode = 2
	}
};
dhtmlXTreeObject.prototype._loadDynXML = function(a, b) {
	var b = b || this.XMLsource,
	c = (new Date).valueOf();
	this._ld_id = a;
	this.xmlalb == "function" ? b && b(this._escape(a)) : this.xmlalb == "name" ? this.loadXML(b + this._escape(a)) : this.xmlalb == "xmlname" ? this.loadXML(b + this._escape(a) + ".xml?uid=" + c) : this.loadXML(b + jv(b) + "uid=" + c + "&id=" + this._escape(a))
};
dhtmlXTreeObject.prototype.enableMultiselection = function(a, b) {
	this._amsel = ap(a);
	this._amselS = ap(b)
};
dhtmlXTreeObject.prototype._checkMSelectionLogic = function() {
	for (var a = [], b = 0; b < this._selected.length; b++) for (var c = 0; c < this._selected.length; c++) b != c && this._checkPNodes(this._selected[c], this._selected[b]) && (a[a.length] = this._selected[c]);
	for (b = 0; b < a.length; b++) this._unselectItem(a[b])
};
dhtmlXTreeObject.prototype._checkPNodes = function(a, b) {
	return this._dcheckf ? !1 : b == a ? 1 : a.parentObject ? this._checkPNodes(a.parentObject, b) : 0
};
dhtmlXTreeObject.prototype.disableDropCheck = function(a) {
	this._dcheckf = ap(a)
};
dhtmlXTreeObject.prototype.enableDistributedParsing = function(a, b, c) {
	this._edsbps = ap(a);
	this._edsbpsA = [];
	this._edsbpsC = b || 10;
	this._edsbpsD = c || 250
};
dhtmlXTreeObject.prototype.getDistributedParsingState = function() {
	return ! (!this._edsbpsA || !this._edsbpsA.length)
};
dhtmlXTreeObject.prototype.getItemParsingState = function(a) {
	var b = this.ak(a, !0, !0);
	if (!b) return 0;
	if (this._edsbpsA) for (var c = 0; c < this._edsbpsA.length; c++) if (this._edsbpsA[c][2] == a) return - 1;
	return 1
};
dhtmlXTreeObject.prototype._distributedStart = function(a, b, c, d, e) {
	if (!this._edsbpsA) this._edsbpsA = [];
	this._edsbpsA[this._edsbpsA.length] = [a, b, c, d, e]
};
dhtmlXTreeObject.prototype._distributedStep = function(a) {
	var b = this;
	if (!this._edsbpsA || !this._edsbpsA.length) b.XMLloadingWarning = 0;
	else {
		var c = this._edsbpsA[0];
		this.parsedArray = [];
		this.Aq(c[0], c[2], c[3], c[1]);
		var d = this.ak(c[2]);
		this._redrawFrom(this, d, c[4], this._getOpenState(d));
		for (var e = this.setCheckList.split(this.dlmtr), f = 0; f < e.length; f++) e[f] && this.setCheck(e[f], 1);
		this._edsbpsA = [].concat(this._edsbpsA.slice(1));
		if (!this._edsbpsA.length) window.setTimeout(function() {
			if (b.onXLE) b.onXLE(b, a);
			b.callEvent("onXLE", [b, a])
		},
		1),
		b.xmlstate = 0
	}
};
dhtmlXTreeObject.prototype.enableTextSigns = function(a) {
	this._txtimg = ap(a)
};
dhtmlXTreeObject.prototype.vq = function(a) {
	this.ir = ap(a);
	this.dF.hN = this.ir
};
dhtmlXTreeObject.prototype.jJ = dhtmlXTreeObject.prototype.vq;
dhtmlXTreeObject.prototype.disableCheckbox = function(a, b) {
	var c = typeof a != "object" ? this.ak(a, 0, 1) : a;
	if (c && (c.dscheck = ap(b) ? (c.checkstate || 0) % 3 + 3 : c.checkstate > 2 ? c.checkstate - 3 : c.checkstate, this._setCheck(c), c.dscheck < 3)) c.dscheck = !1
};
dhtmlXTreeObject.prototype.smartRefreshBranch = function(a, b) {
	this._branchUpdate = 1;
	this.smartRefreshItem(a, b)
};
dhtmlXTreeObject.prototype.smartRefreshItem = function(a, b) {
	for (var c = this.ak(a), d = 0; d < c.aE; d++) c.childNodes[d]._dmark = !0;
	this.waitUpdateXML = !0;
	b && b.exists ? this.Aq(b, a) : this._loadDynXML(a, b)
};
dhtmlXTreeObject.prototype.refreshItems = function(a, b) {
	var c = a.toString().split(this.dlmtr);
	this.waitUpdateXML = [];
	for (var d = 0; d < c.length; d++) this.waitUpdateXML[c[d]] = !0;
	this.loadXML((b || this.XMLsource) + jv(b || this.XMLsource) + "ids=" + this._escape(a))
};
dhtmlXTreeObject.prototype.updateItem = function(a, b, c, d, e, f, g) {
	var h = this.ak(a);
	h.userData = new cObject;
	if (b) h.label = b;
	h.images = [c || this.imageArray[0], d || this.imageArray[1], e || this.imageArray[2]];
	this.setItemText(a, b);
	f && this._setCheck(h, !0);
	if (g == "1" && !this.hasChildren(a)) h.XMLload = 0;
	this._correctPlus(h);
	h._dmark = !1;
	return h
};
dhtmlXTreeObject.prototype.setDropHandler = function(a) {
	this.attachEvent("onDrop", a)
};
dhtmlXTreeObject.prototype.setOnLoadingStart = function(a) {
	this.attachEvent("onXLS", a)
};
dhtmlXTreeObject.prototype.setOnLoadingEnd = function(a) {
	this.attachEvent("onXLE", a)
};
dhtmlXTreeObject.prototype.rx = function(a) {
	this.xmlalb = a
};
dhtmlXTreeObject.prototype.enableSmartCheckboxes = function(a) {
	this.smcheck = ap(a)
};
dhtmlXTreeObject.prototype.getXMLState = function() {
	return this.xmlstate == 1
};
dhtmlXTreeObject.prototype.setItemTopOffset = function(a, b) {
	var c;
	c = typeof a != "object" ? this.ak(a) : a;
	var d = c.span.parentNode.parentNode;
	c.span.style.paddingBottom = "1px";
	for (var e = 0; e < d.childNodes.length; e++) {
		if (e != 0) _isIE ? (d.childNodes[e].style.height = "18px", d.childNodes[e].style.paddingTop = parseInt(b) + "px") : d.childNodes[e].style.height = 18 + parseInt(b) + "px";
		else {
			var f = d.childNodes[e].firstChild;
			d.childNodes[e].firstChild.tagName != "DIV" && (f = document.createElement("DIV"), d.childNodes[e].insertBefore(f, d.childNodes[e].firstChild));
			f.style.height = parseInt(b) + "px";
			if ((c.parentObject.id != this.fO || c.parentObject.childNodes[0] != c) && this.treeLinesOn) f.style.backgroundImage = "url(" + this.imPath + this.lineArray[5] + ")";
			f.innerHTML = "&nbsp;";
			f.style.overflow = "hidden";
			parseInt(b) == 0 && d.childNodes[e].removeChild(f)
		}
		if (!_isIE) d.childNodes[e].style.verticalAlign = "bottom";
		if (_isIE) this.cB.childNodes[0].border = "1",
		this.cB.childNodes[0].border = "0"
	}
};
dhtmlXTreeObject.prototype.yZ = function(a, b, c) {
	if (c) {
		var d = c && c.span ? c: this.ak(c);
		if (!d) return 0;
		var e = d.span.parentNode.previousSibling.childNodes[0];
		if (a && (e.style.width = a, window._KHTMLrv)) e.parentNode.style.width = a;
		if (b && (e.style.height = b, window._KHTMLrv)) e.parentNode.style.height = b
	} else this.def_img_x = a,
	this.def_img_y = b
};
dhtmlXTreeObject.prototype.getItemImage = function(a, b, c) {
	var d = this.ak(a);
	if (!d) return "";
	var e = d.images[b || 0];
	c && (e = this.iconURL + e);
	return e
};
dhtmlXTreeObject.prototype.enableRadioButtons = function(a, b) {
	if (arguments.length == 1) this._frbtr = ap(a),
	this.checkBoxOff = this.checkBoxOff || this._frbtr;
	else {
		var c = this.ak(a);
		if (!c) return "";
		if ((b = ap(b)) && !c._r_logic) {
			c._r_logic = !0;
			for (var d = 0; d < c.aE; d++) this._setCheck(c.childNodes[d], c.childNodes[d].checkstate)
		}
		if (!b && c._r_logic) {
			c._r_logic = !1;
			for (d = 0; d < c.aE; d++) this._setCheck(c.childNodes[d], c.childNodes[d].checkstate)
		}
	}
};
dhtmlXTreeObject.prototype.enableSingleRadioMode = function(a) {
	this._frbtrs = ap(a)
};
dhtmlXTreeObject.prototype.openOnItemAdded = function(a) {
	this._hAdI = !ap(a)
};
dhtmlXTreeObject.prototype.openOnItemAdding = function(a) {
	this._hAdI = !ap(a)
};
dhtmlXTreeObject.prototype.enableMultiLineItems = function(a) {
	this.mlitems = a === !0 ? "100%": a
};
dhtmlXTreeObject.prototype.enableAutoTooltips = function(a) {
	this.ettip = ap(a)
};
dhtmlXTreeObject.prototype.pL = function(a) {
	a ? this._unselectItem(this.ak(a)) : this._unselectItems()
};
dhtmlXTreeObject.prototype.showItemSign = function(a, b) {
	var c = this.ak(a);
	if (!c) return 0;
	var d = c.span.parentNode.previousSibling.previousSibling.previousSibling;
	ap(b) ? (c.closeble = !0, c.wsign = !1) : (this._openItem(c), c.closeble = !1, c.wsign = !0);
	this._correctPlus(c)
};
dhtmlXTreeObject.prototype.showItemCheckbox = function(a, b) {
	if (!a) for (var c in this.gQ) this.showItemCheckbox(this.gQ[c], b);
	typeof a != "object" && (a = this.ak(a, 0, 0));
	if (!a) return 0;
	a.nocheckbox = !ap(b);
	var d = a.span.parentNode.previousSibling.previousSibling.childNodes[0];
	d.style.display = !a.nocheckbox ? "": "none";
	if (window._KHTMLrv) d.parentNode.style.display = !a.nocheckbox ? "": "none"
};
dhtmlXTreeObject.prototype.setListDelimeter = function(a) {
	this.dlmtr = a
};
dhtmlXTreeObject.prototype.setEscapingMode = function(a) {
	this.utfesc = a
};
dhtmlXTreeObject.prototype.enableHighlighting = function(a) {
	this.ehlt = !0;
	this.ehlta = ap(a)
};
dhtmlXTreeObject.prototype._itemMouseOut = function() {
	var a = this.childNodes[3].parentObject,
	b = a.treeNod;
	b.callEvent("onMouseOut", [a.id]);
	if (a.id == b._l_onMSI) b._l_onMSI = null;
	if (b.ehlta) a.span.className = a.span.className.replace("_lor", "")
};
dhtmlXTreeObject.prototype._itemMouseIn = function() {
	var a = this.childNodes[3].parentObject,
	b = a.treeNod;
	b._l_onMSI != a.id && b.callEvent("onMouseIn", [a.id]);
	b._l_onMSI = a.id;
	if (b.ehlta) a.span.className = a.span.className.replace("_lor", ""),
	a.span.className = a.span.className.replace(/((standart|selected)TreeRow)/, "$1_lor")
};
dhtmlXTreeObject.prototype.enableActiveImages = function(a) {
	this._aimgs = ap(a)
};
dhtmlXTreeObject.prototype.Jz = function(a) {
	var b = this.ak(a);
	if (!b) return 0;
	this._focusNode(b)
};
dhtmlXTreeObject.prototype.Fx = function(a) {
	return this._getAllSubItems(a)
};
dhtmlXTreeObject.prototype.getAllChildless = function() {
	return this._getAllScraggyItems(this.ao)
};
dhtmlXTreeObject.prototype.getAllLeafs = dhtmlXTreeObject.prototype.getAllChildless;
dhtmlXTreeObject.prototype._getAllScraggyItems = function(a) {
	for (var b = "",
	c = 0; c < a.aE; c++) if (a.childNodes[c].unParsed || a.childNodes[c].aE > 0) {
		var d = a.childNodes[c].unParsed ? this._getAllScraggyItemsXML(a.childNodes[c].unParsed, 1) : this._getAllScraggyItems(a.childNodes[c]);
		d && (b ? b += this.dlmtr + d: b = d)
	} else b ? b += this.dlmtr + a.childNodes[c].id: b = "" + a.childNodes[c].id;
	return b
};
dhtmlXTreeObject.prototype._getAllFatItems = function(a) {
	for (var b = "",
	c = 0; c < a.aE; c++) if (a.childNodes[c].unParsed || a.childNodes[c].aE > 0) {
		b ? b += this.dlmtr + a.childNodes[c].id: b = "" + a.childNodes[c].id;
		var d = a.childNodes[c].unParsed ? this._getAllFatItemsXML(a.childNodes[c].unParsed, 1) : this._getAllFatItems(a.childNodes[c]);
		d && (b += this.dlmtr + d)
	}
	return b
};
dhtmlXTreeObject.prototype.getAllItemsWithKids = function() {
	return this._getAllFatItems(this.ao)
};
dhtmlXTreeObject.prototype.getAllFatItems = dhtmlXTreeObject.prototype.getAllItemsWithKids;
dhtmlXTreeObject.prototype.getAllChecked = function() {
	return this._getAllChecked("", "", 1)
};
dhtmlXTreeObject.prototype.getAllUnchecked = function(a) {
	a && (a = this.ak(a));
	return this._getAllChecked(a, "", 0)
};
dhtmlXTreeObject.prototype.getAllPartiallyChecked = function() {
	return this._getAllChecked("", "", 2)
};
dhtmlXTreeObject.prototype.getAllCheckedBranches = function() {
	var a = [this._getAllChecked("", "", 1)],
	b = this._getAllChecked("", "", 2);
	b && a.push(b);
	return a.join(this.dlmtr)
};
dhtmlXTreeObject.prototype._getAllChecked = function(a, b, c) {
	if (!a) a = this.ao;
	a.checkstate == c && (a.nocheckbox || (b ? b += this.dlmtr + a.id: b = "" + a.id));
	for (var d = a.aE,
	e = 0; e < d; e++) b = this._getAllChecked(a.childNodes[e], b, c);
	a.unParsed && (b = this._getAllCheckedXML(a.unParsed, b, c));
	return b ? b: ""
};
dhtmlXTreeObject.prototype.setItemStyle = function(a, b, c) {
	var c = c || !1,
	d = this.ak(a);
	if (!d) return 0;
	d.span.style.cssText ? d.span.style.cssText = c ? b: d.span.style.cssText + ";" + b: d.span.setAttribute("style", d.span.getAttribute("style") + "; " + b)
};
dhtmlXTreeObject.prototype.enableImageDrag = function(a) {
	this._itim_dg = ap(a)
};
dhtmlXTreeObject.prototype.setOnDragIn = function(a) {
	this.attachEvent("onDragIn", a)
};
dhtmlXTreeObject.prototype.enableDragAndDropScrolling = function(a) {
	this.autoScroll = ap(a)
};
dhtmlXTreeObject.prototype.setSkin = function(a) {
	var b = this.parentObject.className.replace(/dhxtree_[^ ]*/gi, "");
	this.parentObject.className = b + " dhxtree_" + a;
	a == "dhx_terrace" && this.enableTreeLines(!1)
}; (function() {
	dhtmlx.extend_api("dhtmlXTreeObject", {
		ahY: function(a) {
			return [a.parent, a.width || "100%", a.height || "100%", a.root_id || 0]
		},
		auto_save_selection: "enableAutoSavingSelected",
		auto_tooltip: "enableAutoTooltips",
		checkbox: "enableCheckBoxes",
		checkbox_3_state: "enableThreeStateCheckboxes",
		checkbox_smart: "enableSmartCheckboxes",
		context_menu: "enableContextMenu",
		distributed_parsing: "enableDistributedParsing",
		drag: "enableDragAndDrop",
		drag_copy: "enableMercyDrag",
		drag_image: "enableImageDrag",
		drag_scroll: "enableDragAndDropScrolling",
		editor: "enableItemEditor",
		hover: "enableHighlighting",
		images: "enableTreeImages",
		image_fix: "enableIEImageFix",
		image_path: "setImagePath",
		lines: "enableTreeLines",
		loading_item: "enableLoadingItem",
		multiline: "enableMultiLineItems",
		multiselect: "enableMultiselection",
		navigation: "enableKeyboardNavigation",
		radio: "enableRadioButtons",
		radio_single: "enableSingleRadioMode",
		rtl: "enableRTL",
		search: "enableKeySearch",
		smart_parsing: "enableSmartXMLParsing",
		smart_rendering: "enableSmartRendering",
		text_icons: "enableTextSigns",
		xml: "loadXML",
		skin: "setSkin"
	},
	{})
})();
dhtmlXTreeObject.prototype._dp_init = function(a) {
	a.attachEvent("insertCallback",
	function(a, c, d) {
		var e = this._loader.et(".//item", a),
		f = e[0].getAttribute("text");
		this.obj.insertNewItem(d, c, f, 0, 0, 0, 0, "CHILD")
	});
	a.attachEvent("updateCallback",
	function(a, c, d) {
		var e = this._loader.et(".//item", a),
		f = e[0].getAttribute("text");
		this.obj.setItemText(c, f);
		this.obj.getParentId(c) != d && this.obj.moveItem(c, "item_child", d);
		this.setUpdated(c, !0, "updated")
	});
	a.attachEvent("deleteCallback",
	function(a, c) {
		this.obj.setUserData(c, this.action_param, "true_deleted");
		this.obj.deleteItem(c, !1)
	});
	a._methods = ["setItemStyle", "", "changeItemId", "deleteItem"];
	this.attachEvent("onEdit",
	function(b, c) {
		b == 3 && a.setUpdated(c, !0);
		return ! 0
	});
	this.attachEvent("onDrop",
	function(b, c, d, e, f) {
		e == f && a.setUpdated(b, !0)
	});
	this.ajc = function(b) {
		var c = a.YA(b);
		if (c == "inserted") return a.set_invalid(b, !1),
		a.setUpdated(b, !1),
		!0;
		if (c == "true_deleted") return a.setUpdated(b, !1),
		!0;
		a.setUpdated(b, !0, "deleted");
		return ! 1
	};
	this.BX = function(b) {
		a.setUpdated(b, !0, "inserted")
	};
	a.fD = function(a) {
		for (var c = {},
		d = this.obj.ak(a), e = d.parentObject, f = 0, f = 0; f < e.aE; f++) if (e.childNodes[f] == d) break;
		c.tr_id = d.id;
		c.tr_pid = e.id;
		c.tr_order = f;
		c.tr_text = d.span.innerHTML;
		e = (d.PJ || "").split(",");
		for (f = 0; f < e.length; f++) c[e[f]] = d.userData["t_" + e[f]];
		return c
	}
};