package hibernate;

public class InvoiceDetailsEntity {

	private long detailId;
	private String text;
	private Integer type;

	public InvoiceDetailsEntity() {
		super();
	}

	public InvoiceDetailsEntity(String text, Integer type) {
		super();
		this.text = text;
		this.type = type;
	}

	public long getDetailId() {
		return detailId;
	}

	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
}
