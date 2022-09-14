package com.study.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换的符号
    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();


    @PostConstruct
    public void init(){
        InputStream is =null ;
        BufferedReader reader = null;
        try{

            //这里出过错，文件名字写错了
                is = this.getClass().getClassLoader().getResourceAsStream("senesitive-word.txt");
                reader = new BufferedReader(new InputStreamReader(is));
                String keyWord;
                while((keyWord=reader.readLine())!=null){
                    //添加前缀词
                    this.addKeyWord(keyWord);
                }
        } catch (IOException e) {
            logger.error("加载文件敏感词失败：" + e.getMessage());
        }finally {
            if(is!=null&&reader!=null){
                try{
                    is.close();
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //将一个敏感词添加到前缀树去SBS1R
    private void addKeyWord(String keyWord){
        TrieNode tempNode = rootNode;
        for(int i=0;i<keyWord.length();i++){
            char c = keyWord.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode==null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指向子节点进入下一轮循环
            tempNode = subNode;

            //设置结束标识
            if(i==keyWord.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        //指针1
        TrieNode tempNode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;
        //结果
        StringBuilder sb = new StringBuilder();

        while (begin<text.length()){
            char c = text.charAt(position);

            //跳过符号
            if(isSymbol(c)){
                //若指针1处于根节点，将此符号计入结果，让指针2往下走
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                //无论符号在开头或中间指针3都向下走一步
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode==null){
                sb.append(text.charAt(begin));
                //进入下个位置
                position = ++begin;
                //重新指向根节点
                tempNode = rootNode;
            }else if(tempNode.isKeyWordEnd()){
                //发现敏感词
                sb.append(REPLACEMENT);
                //进入下个位置
                begin = ++position;
                //重新指向根节点
                tempNode = rootNode;
            }else{
                //检查下个字符
                if(position<text.length()-1){
                    position++;
                }else {
                    position = begin;
                }
            }
        }
        //将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    //判断是否未符号
    private boolean isSymbol(Character c){
        //0x2e80~0x9fff 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c)&&(c<0x2E80||c>0x9FFF);
    }
    //前缀树
    private class TrieNode{
        //关键词结束的标识
        private boolean isKeyWordEnd = false;

        //子节点(key是下级字符，value是下级节点)
        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        //添加子节点
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c,node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
