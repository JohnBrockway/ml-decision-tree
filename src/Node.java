/**
 * A class to represent a node in the tree, which is defined as a linked list of these Nodes.
 * @author John Brockway
 */
public class Node {
	/**
	 * Whether this node is a result node
	 */
	boolean isLeaf;
	/**
	 * Only set if isLeaf is true; specifies whether a case that arrives here in the tree is a positive or negative example
	 */
	boolean isHealthy;
	/**
	 * Only set if isLeaf is false; which attribute the subtrees are split on
	 */
	Attribute attribute;
	/**
	 * Only set if isLeaf is false; what threshold the subtrees are split on
	 */
	double threshold;
	/**
	 * The left subtree
	 */
	Node lessThanChildren;
	/**
	 * The right subtree
	 */
	Node greaterThanChildren;

	/**
	 * Default constructor setting isLeaf to false
	 */
	public Node() {
		isLeaf = false;
	}

	/**
	 * Constructor for a leaf node, allowing specification of whether this is a positive or negative case
	 * @param isHealthy Whether this result implies healthy or not
	 */
	public Node(boolean isHealthy) {
		isLeaf = true;
		this.isHealthy = isHealthy;
	}
}