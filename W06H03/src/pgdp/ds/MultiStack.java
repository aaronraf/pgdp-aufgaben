package pgdp.ds;

public class MultiStack {

	private final Stack stacks;

	public MultiStack() {
		stacks = new Stack(1);
	}

	// TODO implement missing methods
	public void push(int value) {
		stacks.push(value);
	}

	public int top() {
		return stacks.top();
	}

	public int pop() {
		if (!stacks.checkTop()) {
			return Integer.MIN_VALUE;
		}
		return stacks.pop();
	}

	@Override
	public String toString() {
		return stacks.toString();
	}

}
