/**
 * 用枚举来实现单例模式是迄今为止最安全的用法
 * @author Samuel_Shen
 *
 */
public class Singleton {

	/**
	 * 私有构造函数，保证不能直接实例化
	 */
	private Singleton() {}
	
	/**
	 * 只能通过调用getInstance方法来实例化，返回的实例将在静态枚举中定义
	 * @return
	 */
	public Singleton getInstance() {
		return SingletonEnum.INSTANCE.getInstance();
	}
	
	/**
	 * 静态的内部枚举，所有成员全都是静态的
	 * @author Samuel_Shen
	 *
	 */
	private static enum SingletonEnum {
		
		INSTANCE;
		
		private Singleton singleton;
		
		private SingletonEnum() {
			singleton = new Singleton();
		}
		
		public Singleton getInstance() {
			return singleton;
		}
	}
}
