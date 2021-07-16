package Handler;

import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum TaskType implements IGetHandler {
	Task_1() {
		public AbstractHandler getHandler(@NotNull ObjectInputStream objectInputStream,
										  @NotNull ObjectOutputStream objectOutputStream) {
			return new Task1Handler(objectInputStream, objectOutputStream);
		}
	},
	Task_2() {
		public AbstractHandler getHandler(@NotNull ObjectInputStream objectInputStream,
										  @NotNull ObjectOutputStream objectOutputStream) {
			return new Task2Handler(objectInputStream, objectOutputStream);
		}
	},
	Task_3() {
		public AbstractHandler getHandler(@NotNull ObjectInputStream objectInputStream,
										  @NotNull ObjectOutputStream objectOutputStream) {
			return new Task3Handler(objectInputStream, objectOutputStream);
		}
	},
	Task_4() {
		public AbstractHandler getHandler(@NotNull ObjectInputStream objectInputStream,
										  @NotNull ObjectOutputStream objectOutputStream) {
			return new Task4Handler(objectInputStream, objectOutputStream);
		}
	};
}


interface IGetHandler {
	AbstractHandler getHandler(@NotNull ObjectInputStream objectInputStream,
							   @NotNull ObjectOutputStream objectOutputStream);
}
