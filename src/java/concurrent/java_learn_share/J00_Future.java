package java_learn_share;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class J00_Future {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		// 执行器
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		// 任务
		Callable<Integer> task = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int result = 0;
				for (int i = 1; i<= 100; i++) {
					result += i;
				}
				Thread.sleep(1000);
				return result;
			}
		};
		
		// 提交任务，获取回执
		Future<Integer> receipt = executor.submit(task);
		System.out.println("提交任务");
		
		receipt.cancel(false);
		
		// 通过回执获取结果
		Integer result = receipt.get();
		
		System.out.println("任务结果：" + result);
	}

}
