package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 同业客户业务明细表
 * tradeCustomerServiceDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TradeCustomerServiceDetails")
public class TradeCustomerServiceDetails implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	
	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "customerName", length = 50)
	@IColumn(description="客户名称")
	private String customerName;
	
	
	@Column(name = "customerCode", length = 50)
	@IColumn(description="客户代码")
	private String customerCode;
	
	@Id
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码")
	private String countryCode;
	
	@Column(name = "nonSpotInstitutCode", length = 50)
	@IColumn(description="非现场监管统计机构编码")
	private String nonSpotInstitutCode;
	
	@Column(name = "organizationCode", length = 50)
	@IColumn(description="组织机构代码")
	private String organizationCode;
	
	@Column(name = "customerCategories", length = 50)
	@IColumn(description="客户类别")
	private String customerCategories;
	
	@Column(name = "internalRate", length = 50)
	@IColumn(description="内部评级")
	private String internalRate;
	
	@Column(name = "externalRate", length = 50)
	@IColumn(description="外部评级")
	private String externalRate;
	
	@Column(name = "interbankOffers", length = 50)
	@IColumn(description="拆放同业")
	private String interbankOffers;
	
								
	@Column(name = "nostro", length = 50)
	@IColumn(description="存放同业")
	private String nostro;
	
	@Column(name = "bondRepurchase", length = 50)
	@IColumn(description="债券回购")
	private String bondRepurchase;
	
	
	@Column(name = "stockPledgloan", length = 50)
	@IColumn(description="股票质押贷款")
	private String stockPledgloan;
	
	@Column(name = "buyBackSaleAsset", length = 50)
	@IColumn(description="买入返售资产")
	private String buyBackSaleAsset;
	
	@Column(name = "outrightTransferDiscount", length = 50)
	@IColumn(description="买断式转贴现")
	private String outrightTransferDiscount;
	
	@Column(name = "holdBond", length = 50)
	@IColumn(description="持有债券")
	private String holdBond;
	
	@Column(name = "equityInvestment", length = 50)
	@IColumn(description="股权投资")
	private String equityInvestment;

					
	@Column(name = "interbankPayment", length = 50)
	@IColumn(description="同业代付")
	private String interbankPayment;
	
	@Column(name = "otherTableBusiness", length = 50)
	@IColumn(description="其他表内业务")
	private String otherTableBusiness;
	
	@Column(name = "repurchaseAssets", length = 50)
	@IColumn(description="卖出回购资产")
	private String repurchaseAssets;
	
	@Column(name = "noIrrevocabLiabilities", length = 50)
	@IColumn(description="不可撤销的承诺及或有负债")
	private String noIrrevocabLiabilities;
	

	@Column(name = "otherOffSheetBusiness", length = 50)
	@IColumn(description="其他表外业务")
	private String otherOffSheetBusiness;

	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getNonSpotInstitutCode() {
		return nonSpotInstitutCode;
	}

	public void setNonSpotInstitutCode(String nonSpotInstitutCode) {
		this.nonSpotInstitutCode = nonSpotInstitutCode;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCustomerCategories() {
		return customerCategories;
	}

	public void setCustomerCategories(String customerCategories) {
		this.customerCategories = customerCategories;
	}

	public String getInternalRate() {
		return internalRate;
	}

	public void setInternalRate(String internalRate) {
		this.internalRate = internalRate;
	}

	public String getExternalRate() {
		return externalRate;
	}

	public void setExternalRate(String externalRate) {
		this.externalRate = externalRate;
	}

	public String getInterbankOffers() {
		return interbankOffers;
	}

	public void setInterbankOffers(String interbankOffers) {
		this.interbankOffers = interbankOffers;
	}

	public String getNostro() {
		return nostro;
	}

	public void setNostro(String nostro) {
		this.nostro = nostro;
	}

	public String getBondRepurchase() {
		return bondRepurchase;
	}

	public void setBondRepurchase(String bondRepurchase) {
		this.bondRepurchase = bondRepurchase;
	}

	public String getStockPledgloan() {
		return stockPledgloan;
	}

	public void setStockPledgloan(String stockPledgloan) {
		this.stockPledgloan = stockPledgloan;
	}

	public String getBuyBackSaleAsset() {
		return buyBackSaleAsset;
	}

	public void setBuyBackSaleAsset(String buyBackSaleAsset) {
		this.buyBackSaleAsset = buyBackSaleAsset;
	}

	public String getOutrightTransferDiscount() {
		return outrightTransferDiscount;
	}

	public void setOutrightTransferDiscount(String outrightTransferDiscount) {
		this.outrightTransferDiscount = outrightTransferDiscount;
	}

	public String getHoldBond() {
		return holdBond;
	}

	public void setHoldBond(String holdBond) {
		this.holdBond = holdBond;
	}

	public String getEquityInvestment() {
		return equityInvestment;
	}

	public void setEquityInvestment(String equityInvestment) {
		this.equityInvestment = equityInvestment;
	}

	public String getInterbankPayment() {
		return interbankPayment;
	}

	public void setInterbankPayment(String interbankPayment) {
		this.interbankPayment = interbankPayment;
	}

	public String getOtherTableBusiness() {
		return otherTableBusiness;
	}

	public void setOtherTableBusiness(String otherTableBusiness) {
		this.otherTableBusiness = otherTableBusiness;
	}

	public String getRepurchaseAssets() {
		return repurchaseAssets;
	}

	public void setRepurchaseAssets(String repurchaseAssets) {
		this.repurchaseAssets = repurchaseAssets;
	}

	public String getNoIrrevocabLiabilities() {
		return noIrrevocabLiabilities;
	}

	public void setNoIrrevocabLiabilities(String noIrrevocabLiabilities) {
		this.noIrrevocabLiabilities = noIrrevocabLiabilities;
	}

	public String getOtherOffSheetBusiness() {
		return otherOffSheetBusiness;
	}

	public void setOtherOffSheetBusiness(String otherOffSheetBusiness) {
		this.otherOffSheetBusiness = otherOffSheetBusiness;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}



	

}



