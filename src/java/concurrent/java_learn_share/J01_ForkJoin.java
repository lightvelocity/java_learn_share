package java_learn_share;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class J01_ForkJoin {
	
	private static class CountTask extends RecursiveTask<Long> {
		private static final long serialVersionUID = -1696191642934332200L;
		
		private int start;
		private int end;
		
		CountTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			if (end - start < 10) {
				Long result = 0L;
				for (int i = start; i <= end; i++) {
					result += i;
				}
				return result;
			} else {
				int middle = (start + end) / 2;
				CountTask task1 = new CountTask(start, middle);
				CountTask task2 = new CountTask(middle+1, end);
				task1.fork();
				task2.fork();
				Long result1 = task1.join();
				Long result2 = task2.join();
				return result1 + result2;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		CountTask task = new CountTask(1, 100000);
		Future<Long> future = pool.submit(task);
		Long result = future.get();
		System.out.println(result);
	}

}
