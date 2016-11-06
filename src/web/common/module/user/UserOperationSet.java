package web.common.module.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserOperationSet {

	private Set<String> set;

	public UserOperationSet() {
		super();

		this.set = new HashSet<String>();
	}

	public void add(List<Operation> list) {
		for (Operation operation : list) {
			this.set.add(operation.getCode());
		}
	}

	public boolean exists(String code) {
		return this.set.contains(code);
	}

}
