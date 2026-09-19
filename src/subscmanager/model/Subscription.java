package subscmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Subscription {
	private String id;
	private String name;
	private Category category;
	private BillingCycle billingCycle;
	private int price;
	private LocalDate nextBillingDate;
	private UsageFrequency frequency;

	public Subscription(String id, String name, Category category, BillingCycle billingCycle,
			int price, LocalDate nextBillingDate, UsageFrequency frequency) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.billingCycle = billingCycle;
		this.price = price;
		this.nextBillingDate = nextBillingDate;
		this.frequency = frequency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BillingCycle getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(BillingCycle billingCycle) {
		this.billingCycle = billingCycle;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		if (price < 0) {
			System.out.println("料金は0以上を入力してください。");

		} else {
			this.price = price;
		}
	}

	public LocalDate getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(LocalDate nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}

	// 日付の判定用メソッド（Stringで受け取って検証・パースする）
	public static LocalDate parseDateOrNull(String value) {
		if (value == null) {
			return null;
		}
		try {
			String checkDate = value.replace("-", "").replace("/", "");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd")
					.withResolverStyle(ResolverStyle.STRICT);

			return LocalDate.parse(checkDate, formatter);
		} catch (Exception e) {
			return null;
		}
	}

	// 外部（コントローラーなど）から呼び出して日付の正誤を判定するメソッド
	public static boolean isDate(String value) {
		return parseDateOrNull(value) != null;
	}

	public UsageFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(UsageFrequency frequency) {
		this.frequency = frequency;
	}
}
