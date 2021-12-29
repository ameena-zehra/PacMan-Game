
public class BinarySearchTree implements BinarySearchTreeADT {
	private BinaryNode r;
	
	// This will create a tree whose root is a leaf node. 
	// Besides the constructor, the only other public methods in this class are specified in the BinarySearchTreeADT interface and described below
	// In all these methods, parameter r is the root of the tree.
	public BinarySearchTree() {
		r = new BinaryNode();
	}

	@Override
	public BinaryNode getRoot() {
		return r;
	}

	@Override
	// Returns the Pixel storing the given key
	// if the key is stored in the tree; returns null otherwise.
	public Pixel get(BinaryNode r, Location key) {
		if (r.isLeaf()){
			return null;
		}
		if (key.compareTo(r.getData().getLocation())==0) {
			return r.getData();
		}
		else if (key.compareTo(r.getData().getLocation())==-1) {
//			if (r.getLeft()!=null) {
				return get(r.getLeft(), key);
//			}
		}
//		if (r.getRight()!=null) {
			return get(r.getRight(), key);
//		}
//		System.out.println("is this oneee in the pixel get operation ever reached");
//		return null;
	}
	
	private BinaryNode getBinaryNode(BinaryNode r, Location key) {
		if (r.isLeaf()){
			return r;
		}
		if (key.compareTo(r.getData().getLocation())==0) {
			return r;
		}
		if (key.compareTo(r.getData().getLocation())==-1) {
			return getBinaryNode(r.getLeft(), key);
		}
		
		return getBinaryNode(r.getRight(), key);
	}

	@Override
	public void put(BinaryNode r, Pixel data) throws DuplicatedKeyException {
		// Inserts the given data in the tree if no data item with the same key is already there
		// If a node already stores the same key
		// the algorithm throws a DuplicatedKeyException.
		Pixel p = get(r, data.getLocation());
		if (p==null) { // when p is a leaf and no data item with the same key/location exists
			
			BinaryNode parentofP = getBinaryNode(r, data.getLocation()).getParent();
			BinaryNode leafnodechildren = new BinaryNode();
			BinaryNode newNode = new BinaryNode(data, leafnodechildren, leafnodechildren, parentofP);
			leafnodechildren.setParent(newNode);
			if (parentofP!=null) {
				if (data.getLocation().compareTo(parentofP.getData().getLocation())==-1) {
					parentofP.setLeft(newNode);
				}
				else {
					parentofP.setRight(newNode);
				}
			}
			else {
				// when the tree is empty and the newNode is the first root node to be added
				this.r = newNode;
			}
		}
		else {
			throw new DuplicatedKeyException();
		}
	}

	@Override
	public void remove(BinaryNode r, Location key) throws InexistentKeyException {
		Pixel p = get(r, key);
		if (p==null) {
			System.out.println("in this case the p is equal to nullkkk");
			throw new InexistentKeyException();
		}
		else {
			
			BinaryNode nodetoRemove = getBinaryNode(r, key);
			if ((nodetoRemove.getLeft().isLeaf())|| (nodetoRemove.getRight().isLeaf())) {
				
				BinaryNode parentNode = nodetoRemove.getParent();
				BinaryNode childNode=null;
				if (nodetoRemove.getLeft().isLeaf()) {
					childNode = nodetoRemove.getRight();
				}
				else {
					childNode = nodetoRemove.getLeft();
				}
				if ((nodetoRemove == this.r)||(parentNode==null)) {
					System.out.println("deleting root");
					this.r = childNode;
					childNode.setParent(null);
				}
				else {
					
					childNode.setParent(parentNode);
					if (parentNode.getLeft()==nodetoRemove) {
						parentNode.setLeft(childNode);
					}
					else {
						parentNode.setRight(childNode);
					}
//					if (childNode.isLeaf()!=true) {
//						if(childNode.getData().getLocation().compareTo(parentNode.getData().getLocation())==-1) {
//							System.out.println("for test 4 is this reached");
//							parentNode.setLeft(childNode);
//						}
//						else {
//							System.out.println("for test 4 is this reached");
//							parentNode.setRight(childNode);
//						}
//					}
//					parentNode.set
				}
			}
			else {
				System.out.println("is this ever reached");
				Pixel s = smallest(nodetoRemove.getRight());
				BinaryNode replacementNode = getBinaryNode(r, s.getLocation());
				// copy key data from 's' to 'p'
		
				nodetoRemove = replacementNode;
				
				remove(replacementNode, replacementNode.getData().getLocation());
			}
		}
		
	}

	@Override
	public Pixel successor(BinaryNode r, Location key) {
		if (r.isLeaf()) {
			System.out.println("is this ever enteredjjj");
			return null;
		}
		else {
			
			Pixel p = get(r, key);
			if (p!=null) {
				
				BinaryNode currentNode = getBinaryNode(r, key);
				if ((currentNode.isLeaf()==false)&&(currentNode.getRight().isLeaf()==false)) {
					return smallest(currentNode.getRight());
				}
				else {
					BinaryNode parentNode = currentNode.getParent();
					while ((currentNode!=this.r)&&((parentNode.getRight()==currentNode))){
						System.out.println("is this ever enteredjjj");
						currentNode =parentNode;
						parentNode = currentNode.getParent();
					}
					if (currentNode==this.r) {
						return null;
					}
					else {
						return parentNode.getData();
					}
				}
			}
			return null;
			
		}
	}

	@Override
	public Pixel predecessor(BinaryNode r, Location key) {
		if (r.isLeaf()) {
			return null;
		}
		else {
			Pixel p = get(r, key);
			if (p!=null) {
				BinaryNode currentNode = getBinaryNode(r, key);
				if ((currentNode.isLeaf()==false)&&(currentNode.getLeft().isLeaf()==false)) {
					return largest(currentNode.getLeft());
				}
				else {
					BinaryNode parentNode = currentNode.getParent();
					while ((currentNode!=this.r)&&((parentNode.getLeft()==currentNode))){
						currentNode =parentNode;
						parentNode = currentNode.getParent();
					}
					if (currentNode==this.r) {
						return null;
					}
					else {
						return parentNode.getData();
					}
				}
			}
			return null;
			
		}
	}

	@Override
	public Pixel smallest(BinaryNode r) throws EmptyTreeException {
		// Returns the Pixel with the smallest key. Throws an EmptyTreeException if the tree does not contain any data.
		if (r==null) {
			System.out.println("is this ever reached");
			throw new EmptyTreeException();
		}
		else {
			if (r.getLeft().isLeaf()==false) {
				return smallest(r.getLeft());
			}
			else {
				return r.getData();
			}
		}
	}

	@Override
	public Pixel largest(BinaryNode r) throws EmptyTreeException {
		if (r==null) {
			System.out.println("is this ever reached");
			throw new EmptyTreeException();
		}
		else {
			if (r.getRight().isLeaf()==false) {
				return largest(r.getRight());
			}
			else {
				return r.getData();
			}
		}
	}

}
