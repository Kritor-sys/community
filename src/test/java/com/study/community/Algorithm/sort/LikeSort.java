package com.study.community.Algorithm.sort;

public class LikeSort {

    private int mod = 1000000007;

    /**
     *请实现无重复数字的升序数组的二分查找
     * @param nums
     * @param target
     * @return
     */
    public int search (int[] nums, int target) {
        // write code here
        return search(nums,0,nums.length-1,target);

    }

    public int search(int[] nums, int left, int right,int target){
        if(left > right){
            return -1;
        }
        int mid = left + (right-left)/2;
        if(nums[mid] == target){
            return mid;
        }else if (target < nums[mid]){
            return search(nums,left,mid-1,target);
        }else{
            return search(nums,mid+1,right,target);
        }
    }

    public boolean Find(int target, int [][] array) {
        if(array==null||array.length==0||array[0].length==0){
            return false;
        }
        //多少行
        int rows = array.length;
        //多少列
        int cols = array[0].length;
        int row  = 0;
        int col = cols-1;
        while(row<rows&&col>=0){
            //与目标值相等
            if(array[row][col]==target){
                return true;
                //小于目标值row++
            }else if(array[row][col]<target){
                row++;
                //大于目标值col--
            }else if(array[row][col]>target){
                col--;
            }
        }
        return false;
    }

    public int findPeakElement (int[] nums) {
        // write code here
        int left = 0;
        int right = nums.length-1;
        while (left<right){
            int mid = left+right>>1;
            if(nums[mid]<nums[mid+1]){
                left = mid+1;
            }else {
                right = mid;
            }
        }
        return left;
    }
//
//    public int InversePairs(int [] array) {
//
//    }

    public int mergeSort(int left, int right, int [] data, int [] temp) {
        return 0;
    }

    public int minNumberInRotateArray(int [] array) {
        int len = array.length;
        int left = 0;
        int right = len-1;

        while(left<right){
            if(array[left]<array[right]){
                return array[left];
            }
            int mid =  left+right>>1;
            if(array[mid]>array[right]){
                left = mid+1;
            }
            else if(array[right]>array[mid]){
                right = mid;
            }else {
                right--;
            }
        }
        return array[left];
    }

    public int compare (String version1, String version2) {
        // write code here
        String[] str1=version1.split("\\.");
        String[] str2=version2.split("\\.");
        int n=Math.max(str1.length,str2.length);
        for(int i=0;i<n;i++){
            int x= i<str1.length ? Integer.valueOf(str1[i]) : 0 ;
            int y= i<str2.length ? Integer.valueOf(str2[i]) : 0 ;
            if(x<y){
                return -1;
            }
            else if(x>y){
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String str = "1.2.3";
        String[] split = str.split("[.]");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }

}
