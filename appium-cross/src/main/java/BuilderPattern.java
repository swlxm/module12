/**
 * 相比构造函数和静态方法，Builder模式更适合参数比较多，且可变的情况。
 * @author Samuel_Shen
 *
 */
public class BuilderPattern {

	private final int servingSize;
	private final int servings;
	private final int fat;
	private final int cal;
	
	/**
	 * 静态的内部类
	 * @author Samuel_Shen
	 *
	 */
	public static class Builder {

		private final int servingSize;
		private final int servings;
		private int fat = 0;
		private int cal = 0;
		
		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder fat(int val) {
			this.fat = val;
			return this;
		}
		
		public Builder cal(int val) {
			this.cal = val;
			return this;
		}
		
		public BuilderPattern build() {
			return new BuilderPattern(this);
		}
	}
	
	public static void main(String[] args) {
		BuilderPattern bp = new BuilderPattern.Builder(2, 3).fat(100).cal(2).build();

	}
	
	/**
	 * 私有的构造函数
	 * @param builder
	 */
	private BuilderPattern(Builder builder) {
		this.servingSize = builder.servingSize;
		this.servings = builder.servings;
		this.fat = builder.fat;
		this.cal = builder.cal;
	}

}
