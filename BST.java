public class BST
{
    private BST_Node root;

    public BST(int rootData)
    {
        root = new BST_Node(rootData);
    }

    public void add(int data) throws Exception
    {
        if(contains(data)) throw new Exception("Duplicated Data");

        //'current' will help finding the correct position to place the new node.
        //But in order to place the node in the correct position, we need a node that is 1-level up.
        //'parentOfCurrent' will be one-level up of 'current'.
        BST_Node current = root;
        BST_Node parentOfCurrent;

        while(true)
        {
            parentOfCurrent = current;

            //If-else checks if the node should go left or right
            if(data <= current.getData())
            {
                if(current.getLeftChild() == null)
                {
                    BST_Node newNode = new BST_Node(data);
                    parentOfCurrent.setLeftChild(newNode);
                    break;
                }
                current = current.getLeftChild();
            }
            else
            {
                if(current.getRightChild() == null)
                {
                    BST_Node newNode = new BST_Node(data);
                    parentOfCurrent.setRightChild(newNode);
                    break;
                }
                current = current.getRightChild();
            }
        }
    }

    public boolean remove(int data)
    {
        if(!contains(data)) return false;

        BST_Node target = root;
        BST_Node targetParent = root;

        //Find the node that needs to be removed.
        while(target.getData() != data)
        {
            targetParent = target;
            if(target.getData() > data) target = target.getLeftChild();
            else target = target.getRightChild();
        }


        //Case 1:Leaf node
        if(target.getLeftChild() == null && target.getRightChild() == null)
        {
            if(target == root) root = null;
            if(targetParent.getLeftChild() == target) targetParent.setLeftChild(null);
            else targetParent.setRightChild(null);
            return true;
        }


        //Case 2:Node that has one child
            //Case 2.1:Node that has one child, and it's on the left.
        if(target.getLeftChild() != null && target.getRightChild() == null)
        {
            if(targetParent.getLeftChild() == target) targetParent.setLeftChild(target.getLeftChild());
            else targetParent.setRightChild(target.getRightChild());
            return true;
        }
            //Case 2.2:Node that has one child, and it's on the right.
        if(target.getRightChild() != null && target.getLeftChild() == null)
        {
            if(targetParent.getLeftChild() == target) targetParent.setLeftChild(target.getRightChild());
            else targetParent.setRightChild(target.getRightChild());
            return true;
        }


        //Case 3:Node that has two children. Find the successor, and have the parent pointing to it.
        BST_Node successor =  findSuccessor(target);
            //Case 3.1:If target is on the left of its parent, change parent left-child pointer to the successor.
        if(targetParent.getLeftChild() == target) targetParent.setLeftChild(successor);
            //Case 3.2:If target is on the right of its parent, change parent right-child pointer to the successor.
        else targetParent.setRightChild(successor);

        return true;
    }

    /**
     *     The successor is the smallest node that is greater than the target.
     *     To do so, we need to go to the right of target FIRST because successor has to be greater than target.
     *     Then find the leftmost node because successor has to be the smallest node that is greater than target.
     * @param toRemove
     * @return successor node
     */
    private BST_Node findSuccessor(BST_Node toRemove)
    {
        BST_Node successorParent = toRemove.getRightChild();
        BST_Node successor = toRemove.getRightChild();

        //Find the successor and its parent.
        while(successor.getLeftChild() != null)
        {
            successorParent = successor;
            successor = successor.getLeftChild();
        }

        //We need to cut the connection between successorParent and successor. Change successorParent left child to successor right child.
        //Then have successor right child point to right child of the node that needs to be removed.
        if(successor != toRemove.getRightChild())
        {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(toRemove.getRightChild());
        }
        successor.setLeftChild(toRemove.getLeftChild());
        return successor;
    }

    public boolean contains(int data)
    {
        BST_Node temp = root;
        while(true)
        {
            if(temp == null) return false;
            if(temp.getData() == data) return true;

            if(temp.getData() > data) temp = temp.getLeftChild();
            else temp = temp.getRightChild();
        }
    }

    /**
     * Functions to find the min or max in the tree. ----------------------------------------------------
     * @return min or max values
     */
    
    public int findMin()
    {
        if(root.getLeftChild() == null) return root.getData();

        BST_Node last = root;
        BST_Node aboveLast = null;

        while(last!=null)
        {
            aboveLast = last;
            last = last.getLeftChild();
        }
        return aboveLast.getData();
    }

    public int findMax()
    {
        if(root.getRightChild() == null) return root.getData();

        BST_Node last = root;
        BST_Node aboveLast = null;

        while(last!=null)
        {
            aboveLast = last;
            last = last.getRightChild();
        }
        return aboveLast.getData();
    }

    /**
     *  Below are sorting functions. ------------------------------------------------------------------
     */
    
    public void preorder()
    {
        preorder(root);
    }

    private void preorder(BST_Node root)
    {
        if(root != null)
        {
            System.out.print(root.getData() + " ");
            preorder(root.getLeftChild());
            preorder(root.getRightChild());
        }
    }

    public void inorder()
    {
        inorder(root);
    }

    private void inorder(BST_Node root)
    {
        if(root != null)
        {
            inorder(root.getLeftChild());
            System.out.print(root.getData() + " ");
            inorder(root.getRightChild());
        }
    }

    public void postorder()
    {
        postorder(root);
    }

    private void postorder(BST_Node root)
    {
        if(root != null)
        {
            postorder(root.getLeftChild());
            postorder(root.getRightChild());
            System.out.print(root.getData() + " ");
        }
    }
}
