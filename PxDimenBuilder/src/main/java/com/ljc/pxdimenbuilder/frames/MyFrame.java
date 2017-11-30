package com.ljc.pxdimenbuilder.frames;

import com.ljc.pxdimenbuilder.documents.NumberLenghtLimitedDmt;
import com.ljc.pxdimenbuilder.beans.WHBean;
import com.ljc.pxdimenbuilder.utils.FileUtil;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

/**
 * Created by lijiacheng on 2017/11/29.
 */

public class MyFrame extends JFrame implements ActionListener {
    JPanel contentPane, pane1, pane2, pane3, list_content;
    JScrollPane list;
    JViewport list_viewport;
    JLabel label1, label11, label2, label22, label222, label3, label33;
    JTextField textField1, textField2, textField3, textField4;
    JButton b1, b2, b3;

    HashMap<String, WHBean> valueList = new HashMap<>();
    int limit;

    public void lunchFrame() {
        this.setTitle("PxDimenBuilder");
        this.setLocation(200, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);


        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        this.setContentPane(contentPane);

        pane1 = new JPanel();
        pane2 = new JPanel();
        list = new JScrollPane();
        list.setSize(500, 500);
        list_viewport = new JViewport();
        list_content = new JPanel();
        label1 = new JLabel("请输入所需像素上限(0-9999)：");
        label11 = new JLabel("Please enter the upper limit of the pixel(0-9999)");
        label3 = new JLabel("请输入精确位数(<=1:小数点后1位;>=2:小数点后2位)默认1位小数");
        label33 = new JLabel("Please enter the exact number of figures(<=1:1;>=2:2)default 1");
        label2 = new JLabel("请在左侧输入高度(如1920)，右侧输入宽度(如1080)(以1080x1920为基准)");
        label22 = new JLabel("Please input the height on the left side(like 1920),Please input the width on the right side(like 1080)");
        label222 = new JLabel("(base on 1080x1920)");


        textField1 = new JTextField();
        textField1.setColumns(1);
        textField1.setDocument(new NumberLenghtLimitedDmt(4));
        textField2 = new JTextField();
        textField2.setColumns(1);
        textField2.setDocument(new NumberLenghtLimitedDmt(4));
        textField3 = new JTextField();
        textField3.setColumns(1);
        textField3.setDocument(new NumberLenghtLimitedDmt(4));
        textField4 = new JTextField();
        textField4.setColumns(1);
        textField4.setDocument(new NumberLenghtLimitedDmt(1));
        b3 = new JButton("add");
        b3.addActionListener(this);
        JPanel pane22 = new JPanel();
        pane22.add(textField2);
        pane22.add(textField3);
        pane22.add(b3);
        pane22.setLayout(new BoxLayout(pane22, BoxLayout.X_AXIS));
        pane1.add(label1);
        pane1.add(label11);
        pane1.add(textField1);
        pane1.add(label3);
        pane1.add(label33);
        pane1.add(textField4);
        pane1.add(label2);
        pane1.add(label22);
        pane1.add(label222);
        pane1.setLayout(new BoxLayout(pane1, BoxLayout.Y_AXIS));
        pane1.add(pane22);

        pane2.add(list);
        pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));
        list_content.setLayout(new BoxLayout(list_content, BoxLayout.Y_AXIS));
        list.setViewport(list_viewport);
        list_viewport.add(list_content);
        contentPane.add(pane1, BorderLayout.NORTH);
        contentPane.add(pane2, BorderLayout.CENTER);


        pane3 = new JPanel();
        JPanel pane4 = new JPanel();

        b1 = new JButton("cancle");
        b1.addActionListener(this);
        b2 = new JButton("ok");
        b2.addActionListener(this);
        pane4.add(b1);
        pane4.add(b2);
        pane4.setLayout(new BoxLayout(pane4, BoxLayout.X_AXIS));
        pane3.setLayout(new BorderLayout());
        pane3.add(pane4, BorderLayout.EAST);
        contentPane.add(pane3, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void addList(int width, int height) {
        this.addList(width, height, true);
    }

    private void addList(int height, int width, boolean isDelete) {
        final String key = height + "x" + width;
        final JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.X_AXIS));
        JLabel w, and, h;
        w = new JLabel(width + "");
        and = new JLabel("x");
        h = new JLabel(height + "");
        item.add(h);
        item.add(and);
        item.add(w);
        if (isDelete) {
            JButton delete = new JButton("delete");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valueList.remove(key);
                    list_content.remove(item);
                    list_content.repaint();
                    MyFrame.this.setVisible(true);
                }
            });
            item.add(delete);
        }

        if (!valueList.containsKey(key)) {
            WHBean value = new WHBean();
            value.setWidth(width);
            value.setHeight(height);
            valueList.put(key, value);
            System.out.println(height + "x" + width);
            list_content.add(item);
            this.setVisible(true);
        } else {
            showDialog("已存在该像素值/it is exist");
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            exit();
        } else if (e.getSource() == b2) {
            String max = textField1.getText().toString();
            if (max.isEmpty()) {
                showDialog("请输入所需像素上限/please input the upper limit");
                return;
            }
            if (valueList.isEmpty()) {
                showDialog("请新增像素值组/please add a height&width");
                return;
            }
            limit = Integer.parseInt(max);
            startBuild();
        } else if (e.getSource() == b3) {
            System.out.println("新增数据/add");
            String h = textField2.getText();
            String w = textField3.getText();
            if (w.isEmpty() || h.isEmpty()) {
                showDialog("请输入像素值/please input the height or width");
                return;
            }
            textField2.setText("");
            textField3.setText("");
            addList(Integer.parseInt(h), Integer.parseInt(w));
        }
    }

    private void showDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void startBuild() {
        System.out.println("开始生成/start create files");
        WHBean fValue = new WHBean();
        fValue.setWidth(1080);
        fValue.setHeight(1920);
        int newScale = Integer.parseInt(textField4.getText().toString());
        if (newScale <= 1) {
            newScale = 1;
        } else {
            newScale = 2;
        }
        FileUtil.createFile("values", fValue, fValue, limit, newScale);
        System.out.println("默认生成结束/default dimen.xml file is finished");
        Map map = valueList;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            System.out.println("开始遍历/go through");
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            WHBean val = (WHBean) entry.getValue();
            FileUtil.createFile("values-" + key, fValue, val, limit, newScale);
        }
        System.out.println("生成结束/all dimen.xml files are finished");
    }

    private void exit() {
        setVisible(false);//隐藏窗体
        System.exit(0);//退出程序
    }
}
