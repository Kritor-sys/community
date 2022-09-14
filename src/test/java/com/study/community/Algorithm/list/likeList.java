package com.study.community.Algorithm.list;

import java.util.*;

public class likeList {

    //定义节点类
    public static  class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

    /**
     * 反转链表：
     * 1.栈
     * 2.双链表
     * 3.递归（直接递归和尾递归）
     *
     */

    /**
     * 用栈实现反转链表
     * @param head
     * @return
     */
    public static ListNode reverseListToStack(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while(head!=null){
            stack.push(head);
            head = head.next;
        }
        if(stack.isEmpty()){
            System.out.println("链表为空");
            return null;
        }
        ListNode node = stack.pop();
        ListNode dummy = node;

        //将栈中的元素全部取出
        while (!stack.isEmpty()){
            ListNode tempNode = stack.pop();
            node.next = tempNode;
            node = node.next;
        }
        //最后一个节点置为空否则会构成环
        node.next = null;
        return dummy;
    }

    /**
     * 用双链表实现反转
     * @param head
     * @return
     */
    public static ListNode reverseListToList2(ListNode head) {
            //创建一个新链表
            ListNode newHead = null;
            while (head!=null){
                //将本节点的下个节点存储起来
                ListNode tempNode = head.next;
                //当前节点指向新链表
                head.next = newHead;
                //更新链表
                newHead = head;
                head = tempNode;
            }
            return newHead;
    }

    /**
     * 用递归实现反转
     * @param head
     * @return
     */
    public static ListNode reverseListToDiGui(ListNode head) {
        //中止条件
        if(head==null||head.next==null){
            return head;
        }
        ListNode reverse = reverseListToStack(head.next);
        head.next.next=head;
        head.next = null;
        return reverse;
    }


    /**
     * 将一个节点数为 size 链表 m 位置到 n 位置之间的区间反转，
     * 要求时间复杂度 O(n)O(n)，空间复杂度 O(1)O(1)。
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseListToList2(ListNode head,int m,int n) {
        //创建虚拟头节点
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i <m-1 ; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode cur_next;
        for (int i = 0; i <n-m; i++) {
            cur_next = cur.next;
            cur.next = cur_next.next ;
            cur_next.next = pre.next;
            pre.next = cur_next;
        }
        return dummy.next;
    }


    /**
     * 将给出的链表中的节点每 k 个一组翻转，返回翻转后的链表
     * 如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
     * 你不能更改节点中的值，只能更改节点本身。
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseListGroup(ListNode head,int k) {
        //找到每次翻转的尾节点
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if(tail==null){
                return head;
            }
            tail = tail.next;
        }

        //反转时需要前序节点和当前节点
        ListNode pre = null;
        ListNode cur = head;
        while (cur!=tail){
            //反转，
            ListNode tmp  =cur.next;
            cur.next = pre;
            pre =cur;
            cur = tmp;
        }
        head.next = reverseListGroup(tail,k);
        return pre;
    }

    /**
     * 输入两个递增的链表，单个链表的长度为n，合并这两个链表并使新链表中的节点仍然是递增排序的。
     * 数据范围： 0 \le n \le 10000≤n≤1000，-1000 \le 节点值 \le 1000−1000≤节点值≤1000
     * 要求：空间复杂度 O(1)O(1)，时间复杂度 O(n)O(n)
     */
    public static ListNode Merge(ListNode list1,ListNode list2) {
        //创建虚拟头节点头节点
        ListNode head = new ListNode(-1);
        //创建头节点变量
        ListNode cur = head;
        ListNode list1Temp = list1;
        ListNode list2Temp = list2;
        while(list1Temp!=null&&list2Temp!=null){
            if(list1Temp.val<list2Temp.val){
                cur.next = list1Temp;
                list1Temp = list1Temp.next;
            }else{
                cur.next = list2Temp;
                list2Temp = list2Temp.next;
            }
            cur = cur.next;
        }
        if(list1Temp!=null){
            cur.next = list1Temp;
        }
        if(list2Temp!=null){
            cur.next = list2Temp;
        }
        return head.next;
    }


    /**
     * 合并 k 个升序的链表并将结果作为一个升序的链表返回其头节点。
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        return mergeList(lists,0,lists.size()-1);
    }
    //分治进行链表两两合并
    public ListNode mergeList(ArrayList<ListNode> lists,int L,int R){
        //终止条件
        if(L==R){
            return lists.get(L);
        }
        if(L>R){
            return null;
        }
        //分治
        int mid = L+((R-L)>>1);
        return merge(mergeList(lists,L,mid),mergeList(lists,mid+1,R));
    }
    //合并两个有序链表
    public ListNode merge(ListNode l1,ListNode l2){
        ListNode head = new ListNode(-1);
        ListNode cur = head;
        if(l1==null||l2==null){
            return l1==null?l2:l1;
        }
        while(l1!=null&&l2!=null){
            if(l1.val<l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1==null?l2:l1;
        return head.next;
    }

    /**
     * 判断链表是否有环
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        //用集合判断是否重复
        HashSet<ListNode> set = new HashSet<>();
        while (head!=null){
            if(set.contains(head)){
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
    public static boolean hasCycle2(ListNode head) {
        //快慢指针
        if(head == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow ==fast){
                return true;
            }
        }
        return false;
    }

    /**
     * 找到环中的入口
     * @param head
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode head) {
        //判断是否有环
        if(head == null){
            return null;
        }
        boolean flag = false;
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow ==fast){
                flag = true;
                break;
            }
        }
        if(!flag){
            return null;
        }else{
            //得到环中的数量
            int n = 1;
            fast = fast.next;
            while(slow!=fast){
                fast = fast.next;
                n++;
            }
            //找到环的入口
            slow = fast = head;
            for (int i = 0; i < n; i++) {
                fast = fast.next;
            }
            while (slow!=fast){
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }
    }


    public ListNode FindKthToTail1 (ListNode pHead, int k) {
        // write code here
        if(pHead == null){
            return null;
        }
        int count = 1;
        ListNode result = pHead;
        while(pHead.next!=null){
            pHead = pHead.next;
            count++;
        }
        if(count<k){
            return null;
        }
        int start = count-k+1;
        int temp = 1;
        while (result!=null){
            if(temp==start){
                return result;
            }
            result = result.next;
            temp++;
        }
        return null;
    }
    public ListNode FindKthToTail2(ListNode pHead, int k) {
        // write code here
        if(pHead == null){
            return null;
        }
        ListNode slow = pHead;
        ListNode fast = pHead;
        for (int i = 0; i < k; i++) {
            if(fast!=null){
                fast = fast.next;
            }else{
                return null;
            }
        }
        while(fast!=null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public ListNode removeNthFromEnd (ListNode head, int n) {
        // write code here

        ListNode slow = head;
        ListNode fast = head;
        if(head == null){
            return null;
        }
        ListNode tmp = head;
        int len = 0;
        while(tmp!=null){
            tmp = tmp.next;
            len++;
        }
        int pre = len-n;
        if(pre==0){
            return head.next;
        }
        for (int i = 0; i < pre-1; i++) {
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;


    }

    /**
     * 两个链表的公共节点
     * @param pHead1
     * @param pHead2
     * @return
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1==null||pHead2==null){
            return null;
        }
        int n = getLength(pHead1) - getLength(pHead2);
        if(n<0){
            n = n*(-1);
            for (int i = 0; i < n; i++) {
                pHead2 = pHead2.next;
            }
        }else {
            for (int i = 0; i < n; i++) {
                pHead1 = pHead1.next;
            }
        }
        while(pHead1.val!=pHead2.val&&pHead1.next!=null){
            pHead1 = pHead1.next;
            pHead2 = pHead2.next;
        }
        if(pHead1.val!=pHead2.val){
            return null;
        }
        return pHead1;
    }

    public static int   getLength(ListNode head){
        if(head==null){
            return 0;
        }
        int len = 0;
        while(head!=null){
            len++;
            head = head.next;
        }
        return  len;
    }

    public ListNode addInList (ListNode head1, ListNode head2) {
            ListNode reverseHead1 = reverseListToList2(head1);
            ListNode reverseHead2 = reverseListToList2(head2);
            int tmp = 0;
            ListNode newHead = null;
            while(reverseHead1!=null||reverseHead2!=null){
                int x1 = reverseHead1==null?0:reverseHead1.val;
                int x2 = reverseHead2==null?0:reverseHead2.val;
                int sum = x1+x2+tmp;
                tmp = sum/10;
                ListNode tmpNode = new ListNode(sum%10);
                tmpNode.next = newHead;
                newHead = tmpNode;
                reverseHead1=reverseHead1==null?null:reverseHead1.next;
                reverseHead2=reverseHead2==null?null:reverseHead2.next;
            }
            if(tmp > 0){
                ListNode tempNode = new ListNode(tmp);
                tempNode.next = newHead;
                newHead = tempNode;
            }

            return newHead;
    }

    /**
     * 判断是否是会问结构
     * @param head
     * @return
     */
    public boolean isPail (ListNode head) {
        // write code here
        if(head==null){
            return true;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast = fast.next.next;
        }
        slow = reverse(slow);
        fast = head;
        while(slow!=null){
            if(slow.val!=fast.val){
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    ListNode reverse(ListNode head){
        ListNode pre = null;
        while(head!=null){
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    /**
     *奇偶重排链表
     * @param head
     * @return
     */
    public ListNode oddEvenList (ListNode head) {
        // write code here
        if(head==null||head.next==null){
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;

        ListNode tmp = fast;
        while (slow.next!=null&&slow.next.next!=null){
            slow.next = slow.next.next;
            fast.next = fast.next.next;
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = tmp;
        return head;
    }


    /**
     * 删除重复的元素
     * @param head
     * @return
     */
    public ListNode deleteDuplicates (ListNode head) {
        // write code here
         if(head==null||head.next==null){
             return head;
         }
         head.next = deleteDuplicates(head.next);
         if(head.val==head.next.val){
            head = head.next;
         }
         return head;
    }

    /**
     * 删除重复的元素
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        // write code here
        if(head==null||head.next==null){
            return head;
        }
        head.next = deleteDuplicates(head.next);
        if(head.val==head.next.val){
            head = head.next.next;
        }
        return head;
    }
    /**
     * 初始化链表
     * 如果需要测试，先构建函数生成链表
     * 在构建打印函数的值
     */
    public static class LinkedListCreator{

        //构建链表
        public static ListNode creatorLinkList(List<Integer> list){
            if(list.isEmpty()){
                return null;
            }
            ListNode headNode = new ListNode(list.get(0));
            ListNode tempNode = creatorLinkList(list.subList(1, list.size()));
            headNode.setNext(tempNode);
            return headNode;
        }

        public static void printList(ListNode node){
            while (node!=null){
                System.out.print(node.getVal()+" ");
                node = node.getNext();
            }
        }
    }

    public static void main(String[] args) {
        ListNode node = LinkedListCreator.creatorLinkList(Arrays.asList(1, 2, 3, 4, 5));
//        ListNode node1 = reverseListToStack(node);
//        ListNode node1 = reverseListToList2(node, 2, 4);
        ListNode node1 = reverseListGroup(node, 2);
        LinkedListCreator.printList(node1);

    }
}
