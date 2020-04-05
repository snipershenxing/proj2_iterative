package com.company;


import java.io.*;
import java.util.*;


public class Main {
    static TreeNode root = null;
    public static void main(String[] args) {
        // write your code here
        String city = "NewYork"; //put your city here to have a check
        javaIterative(city);
    }
    public static void javaIterative(String city) {
        //read txt file into list
        List<String> fileContent = TXTReader.readFile();
        //build bst based on dictionary order
        for (String s : fileContent) {
            insert(s);
        }

        boolean ifContain = false;

        InOrderIterator inOrderIterator = new InOrderIterator(root);
        System.out.println("\n inorder : \n");
        while (inOrderIterator.hasNext()) {
            TreeNode node = inOrderIterator.next();
            System.out.println(node.data);
            if (isContain(city, node.data)) {
                ifContain = true;
            }
        }

        PreOrderIterator preOrderIterator = new PreOrderIterator(root);
        System.out.println("\n preorder : \n");
        while (preOrderIterator.hasNext()) {
            TreeNode node = preOrderIterator.next();
            System.out.println(node.data);
        }

        PostOrderIterator postOrderIterator = new PostOrderIterator(root);
        System.out.println("\n postorder : \n");
        while (postOrderIterator.hasNext()) {
            TreeNode node = postOrderIterator.next();
            System.out.println(node.data);
        }

        if (ifContain) {
            System.out.println("\n Yes, BST contains your input");
        } else {
            System.out.println("\n No, BST doesn't contain your input");
        }
    }
    private static boolean isContain(String city, String data) {
        return city.equals(data);
    }

    private static void insert(String data) {
        TreeNode node = new TreeNode(data);

        if (root == null) {
            root = node;
        } else {
            TreeNode index = root;
            TreeNode current;

            while (true) {
                current = index;
                if (data.compareTo(index.data) < 0) {
                    index = index.left;
                    if (index == null) {
                        current.left = node;
                        break;
                    }
                } else {
                    index = index.right;
                    if (index == null) {
                        current.right = node;
                        break;
                    }
                }
            }
        }
    }
}

class TreeNode{
    public String data;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class TXTReader {
    public static List<String> readFile() {
        String fileName = "data.txt";
        List<String> content = new ArrayList<>();

        try (FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}

class InOrderIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public InOrderIterator(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode cur = stack.pop();

        TreeNode right = cur.right;
        while (right != null) {
            stack.push(right);
            right = right.left;
        }

        return cur;
    }
}

class PreOrderIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public PreOrderIterator (TreeNode root) {
        stack.push(root);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode cur = stack.pop();
        if (cur.right != null) {
            stack.push(cur.right);
        }
        if (cur.left != null) {
            stack.push(cur.left);
        }
        return cur;
    }
}

class PostOrderIterator {
    private Stack<TreeNode> stack = new Stack<>();
    private Set<TreeNode> set = new HashSet<>();

    public PostOrderIterator(TreeNode root) {
        while (root != null) {
            set.add(root);
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode cur = stack.pop();
        TreeNode node = null;
        if (!stack.isEmpty()) {
            node = stack.peek();
        }

        TreeNode right = null;
        if (node != null) {
            right = node.right;
        }

        if (!set.contains(right) && right != null){
            while (right != null) {
                set.add(right);
                stack.push(right);
                right = right.left;
            }
        }

        return cur;
    }
}
