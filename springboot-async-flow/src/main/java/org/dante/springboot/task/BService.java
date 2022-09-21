package org.dante.springboot.task;

import com.gobrs.async.TaskSupport;
import com.gobrs.async.anno.Task;
import com.gobrs.async.task.AsyncTask;

import org.springframework.stereotype.Component;

@Component
@Task
public class BService extends AsyncTask<Object, Object> {

	/**
	 * The .
	 */
	int i = 10000;

	@Override
	public void prepare(Object o) {

	}

	@Override
	public Object task(Object o, TaskSupport support) {
		System.out.println("BService Begin");
		for (int i1 = 0; i1 < i; i1++) {
			i1 += i1;
		}
		System.out.println("BService Finish");
		System.out.println(1 / 0);
		return null;
	}

	@Override
	public boolean nessary(Object o, TaskSupport support) {
		return true;
	}

	@Override
	public void onSuccess(TaskSupport support) {

	}

	@Override
	public void onFail(TaskSupport support) {

	}
}