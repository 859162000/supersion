<!--

function WebOpen()
{
	

	switch(flag)
	{
	case '0':
	
		frm.WebOffice.WebOpen(strOpenUrl);
		break;
	case '2':
		frm.WebOffice.CreateNew("Excel.Sheet");
		break;
	case '3':
		frm.WebOffice.CreateNew("PowerPoint.Show");
		break;
	default:
		frm.WebOffice.CreateNew("Word.Document");
	}

}
   
function WebSave()
{
try
{

	frm.WebOffice.Save(strURL);

}
catch(e)
{

}
return true;
}
function WebSaveLocal()
{
	frm.WebOffice.showdialog(3);
}
function WebOpenLocal()
{
	frm.WebOffice.showdialog(1);
}
function WebDocReload()
{
	location.reload();	
}
function WebOpenPicture()
{
	frm.WebOffice.ActiveDocument.Application.Dialogs(163).Show();
}
function WebDocPrint()
{
	frm.WebOffice.printout(true);
}
function WebDocPageSetup()
{
	frm.WebOffice.showdialog(5);
}
function ShowRevision(boolvalue)
{
	try
	{
	frm.WebOffice.ActiveDocument.ActiveWindow.View.Reviewers(1).Visible =boolvalue; 

	frm.WebOffice.ActiveDocument.TrackRevisions = boolvalue;
}
catch(E)
{
	alert('文档暂无痕迹！');
	}
	//frm.WebOffice.ActiveDocument.ShowComments;
}
function WebDocSignature()
{
	try{
	frm.WebOffice.WebSign();
	
	var test;
	var strFile = frm.WebOffice.WebSignTempFile;
	
	frm.WebOffice.title=strFile;
	var doc = frm.WebOffice.ActiveDocument;
	
	doc.Shapes.AddPicture(frm.WebOffice.title,false,true);
	doc.Shapes(doc.Shapes.Count).Select(); 
	var range = doc.Application.Selection.ShapeRange;
	range.WrapFormat.Type = 3;
	range.PictureFormat.TransparentBackground = true;
	range.PictureFormat.TransparencyColor = 0xFFFFFF;
	range.Fill.Visible = false;
	frm.WebOffice.WebSignTempFileDel(); 
}
catch(E)
{
	
	}
}
function WebTempFile()
{
	//画线
	var object=frm.WebOffice.ActiveDocument;
	var myl=object.Shapes.AddLine(100,60,305,60)
	myl.Line.ForeColor=255;
	myl.Line.Weight=2;
	var myl1=object.Shapes.AddLine(326,60,520,60)
	myl1.Line.ForeColor=255;
	myl1.Line.Weight=2;

	//object.Shapes.AddLine(200,200,450,200).Line.ForeColor=6;
   	var myRange=frm.WebOffice.ActiveDocument.Range(0,0);
	myRange.Select();

	var mtext="★";
	frm.WebOffice.ActiveDocument.Application.Selection.Range.InsertAfter (mtext+"\n");
   	var myRange=frm.WebOffice.ActiveDocument.Paragraphs(1).Range;
   	myRange.ParagraphFormat.LineSpacingRule =1.5;
   	myRange.font.ColorIndex=6;
   	myRange.ParagraphFormat.Alignment=1;
   	myRange=frm.WebOffice.ActiveDocument.Range(0,0);
	myRange.Select();
	mtext="[２００３]１５４号";
	frm.WebOffice.ActiveDocument.Application.Selection.Range.InsertAfter (mtext+"\n");
	myRange=frm.WebOffice.ActiveDocument.Paragraphs(1).Range;
	myRange.ParagraphFormat.LineSpacingRule =1.5;
	myRange.ParagraphFormat.Alignment=1;
	myRange.font.ColorIndex=1;
	
	mtext="电子政务文件";
	frm.WebOffice.ActiveDocument.Application.Selection.Range.InsertAfter (mtext+"\n");
	myRange=frm.WebOffice.ActiveDocument.Paragraphs(1).Range;
	myRange.ParagraphFormat.LineSpacingRule =1.5;
	
	//myRange.Select();
	myRange.Font.ColorIndex=6;
	myRange.Font.Name="仿宋_GB2312";
	myRange.font.Bold=true;
	myRange.Font.Size=50;
	myRange.ParagraphFormat.Alignment=1;
	
	//myRange=myRange=frm.WebOffice.ActiveDocument.Paragraphs(1).Range;
	frm.WebOffice.ActiveDocument.PageSetup.LeftMargin=70;
	frm.WebOffice.ActiveDocument.PageSetup.RightMargin=70;
	frm.WebOffice.ActiveDocument.PageSetup.TopMargin=70;
	frm.WebOffice.ActiveDocument.PageSetup.BottomMargin=70;
}
function WebSetWordTable()
{
var mText="",mTmp="",iColumns,iCells,iPost,iold=-1;
  var myRange=frm.WebOffice.ActiveDocument.Range(0,0);     //光标位置

	frm.WebOffice.ActiveDocument.Tables.Add(myRange,10,10);   //生成表格
	//alert(mText);
	for (var n=0; n<iColumns; n++)
	{
   	  for (var i=0; i<iCells; i++)
	  {
		iPos  = mText.indexOf(";",1+iold);
		mTmp = mText.substring(iold+1,iPos);
		frm.WebOffice.ActiveDocument.Tables(1).Columns(n+1).Cells(i+1).Range.Text=mTmp;   //填充单元值
		iold = iPos; 
	   }
	}
	
   
}
function WebGetWordContent()
{
	try{
    alert(frm.WebOffice.ActiveDocument.Content.Text);
  }catch(e){}
}
function WebSetWordContent()
{
	 var mText=window.prompt("请输入内容:","测试内容");
  if (mText==null){
     return (false);
  }
  else
  {
     //下面为显示选中的文本
     //alert(frm.WebOffice.ActiveDocument.Application.Selection.Range.Text);
     //下面为在当前光标出插入文本
     frm.WebOffice.ActiveDocument.Application.Selection.Range.InsertAfter (mText+"\n");
     //下面为在第一段后插入文本
     //frm.WebOffice.ActiveDocument.Application.ActiveDocument.Range(1).InsertAfter(mText);
  }
}
function WebGetExcelContent()
{
	    frm.WebOffice.ActiveDocument.Application.Sheets(1).Select;
    frm.WebOffice.ActiveDocument.Application.Range("C5").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "126";
    frm.WebOffice.ActiveDocument.Application.Range("C6").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "446";
    frm.WebOffice.ActiveDocument.Application.Range("C7").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "556";
    frm.WebOffice.ActiveDocument.Application.Range("C5:C8").Select;
    frm.WebOffice.ActiveDocument.Application.Range("C8").Activate;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "=SUM(R[-3]C:R[-1]C)";
    frm.WebOffice.ActiveDocument.Application.Range("D8").Select;
    alert(frm.WebOffice.ActiveDocument.Application.Range("C8").Text);
}
//作用：保护工作表单元
function WebSheetsLock(){
    frm.WebOffice.ActiveDocument.Application.Sheets(1).Select;

    frm.WebOffice.ActiveDocument.Application.Range("A1").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "产品";
    frm.WebOffice.ActiveDocument.Application.Range("B1").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "价格";
    frm.WebOffice.ActiveDocument.Application.Range("C1").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "详细说明";
    frm.WebOffice.ActiveDocument.Application.Range("D1").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "库存";
    frm.WebOffice.ActiveDocument.Application.Range("A2").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "书签";
    frm.WebOffice.ActiveDocument.Application.Range("A3").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "毛笔";
    frm.WebOffice.ActiveDocument.Application.Range("A4").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "钢笔";
    frm.WebOffice.ActiveDocument.Application.Range("A5").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "尺子";

    frm.WebOffice.ActiveDocument.Application.Range("B2").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "0.5";
    frm.WebOffice.ActiveDocument.Application.Range("C2").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "樱花";
    frm.WebOffice.ActiveDocument.Application.Range("D2").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "300";

    frm.WebOffice.ActiveDocument.Application.Range("B3").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "2";
    frm.WebOffice.ActiveDocument.Application.Range("C3").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "狼毫";
    frm.WebOffice.ActiveDocument.Application.Range("D3").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "50";

    frm.WebOffice.ActiveDocument.Application.Range("B4").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "3";
    frm.WebOffice.ActiveDocument.Application.Range("C4").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "蓝色";
    frm.WebOffice.ActiveDocument.Application.Range("D4").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "90";

    frm.WebOffice.ActiveDocument.Application.Range("B5").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "1";
    frm.WebOffice.ActiveDocument.Application.Range("C5").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "20cm";
    frm.WebOffice.ActiveDocument.Application.Range("D5").Select;
    frm.WebOffice.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "40";

    //保护工作表
    frm.WebOffice.ActiveDocument.Application.Range("B2:D5").Select;
    frm.WebOffice.ActiveDocument.Application.Selection.Locked = false;
    frm.WebOffice.ActiveDocument.Application.Selection.FormulaHidden = false;
    frm.WebOffice.ActiveDocument.Application.ActiveSheet.Protect(true,true,true);   

    alert("已经保护工作表，只有B2-D5单元格可以修改。");
}

//作用：获取文档页数
function WebDocumentPageCount(){
	var intPageTotal;
	intPageTotal = frm.WebOffice.ActiveDocument.Application.ActiveDocument.BuiltInDocumentProperties(14);
	alert("文档页总数："+intPageTotal);
}
function WebToolbar(boolvalue)
{

frm.WebOffice.Toolbars = boolvalue;

}
function WebTitlebar(boolvalue)
{
	   frm.WebOffice.Titlebar = boolvalue;
	}
	function WebInsertImage()
	{
		frm.WebOffice.ActiveDocument.Application.Selection.InlineShapes.AddPicture("http://www.superoa.net/login.gif",false,true);
				}
//-->