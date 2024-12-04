package pgdp.ds;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stack {
	private Stack next;
	private final int[] mem;
	private int top;

	public Stack(int capacity) {
		next = null;
		mem = new int[capacity];
		top = -1;
	}

	// TODO implement missing methods
	public void push(int value) {
		if (top == mem.length - 1) {
			if (next == null) {
				next = new Stack(mem.length * 2);
			}
			next.push(value);
		} else {
			mem[++top] = value;
		}
	}

	public int top() {
		if (next != null && checkTop() && next.checkTop()) {
			return next.top();
		}

		return checkTop() ? mem[top] : Integer.MIN_VALUE;
	}

	public boolean checkTop() {
		return top > -1;
	}

	public int pop() {
		if (top == mem.length - 1 && next != null) {
			if (checkTop()) {
				return next.pop();
			}

			next = null;
		}

		return mem[top--];
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\ncapacity = ").append(mem.length).append(",\n");
		sb.append("mem = [")
				.append(IntStream.range(0, top + 1).mapToObj(x -> "" + mem[x]).collect(Collectors.joining(", ")))
				.append("],\n");
		sb.append("next = ").append(next == null ? "null" : "\n" + next.toString()).append("\n}\n");
		return sb.toString();
	}
}
