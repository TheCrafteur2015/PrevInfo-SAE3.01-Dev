package vue;

public class RecapIntervenantIHM {

	private String module;
	private String s1;
	private String s3;
	private String s5;
	private String stotimpair;
	private String s2;
	private String s4;
	private String s6;
	private String stotpair;
	private String total;
	
	public RecapIntervenantIHM(String module) {
		this.module = module;
	}
	
	public RecapIntervenantIHM(String module, String s1, String s3, String s5, String stotimpair, String s2, String s4,
			String s6, String stotpair, String total) {
		this.module = module;
		this.s1 = s1;
		this.s3 = s3;
		this.s5 = s5;
		this.stotimpair = stotimpair;
		this.s2 = s2;
		this.s4 = s4;
		this.s6 = s6;
		this.stotpair = stotpair;
		this.total = total;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS3() {
		return s3;
	}

	public void setS3(String s3) {
		this.s3 = s3;
	}

	public String getS5() {
		return s5;
	}

	public void setS5(String s5) {
		this.s5 = s5;
	}

	public String getStotimpair() {
		return stotimpair;
	}

	public void setStotimpair(String stotimpair) {
		this.stotimpair = stotimpair;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public String getS4() {
		return s4;
	}

	public void setS4(String s4) {
		this.s4 = s4;
	}

	public String getS6() {
		return s6;
	}

	public void setS6(String s6) {
		this.s6 = s6;
	}

	public String getStotpair() {
		return stotpair;
	}

	public void setStotpair(String stotpair) {
		this.stotpair = stotpair;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	
	
}
