package com.example.orderapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea, rbCoffee, rbSmoothies;
    private CheckBox cbPearl, cbPudding, cbRedBean, cbCoconut;
    private Button btnMinus, btnPlus, btnAdd, btnDelete, btnReset;
    private TextView txtQty, txtTotal, txtName;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder=new ArrayList<>();
    private long total=0;
    private int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName=(EditText)findViewById(R.id.edName);
        rgType=(RadioGroup) findViewById(R.id.rgType);
        rbTea=(RadioButton)findViewById(R.id.rbTea);
        rbCoffee=(RadioButton)findViewById(R.id.rbCoffee);
        rbSmoothies=(RadioButton)findViewById(R.id.rbSmoothies);
        cbPearl=(CheckBox)findViewById(R.id.cbPearl);
        cbPudding=(CheckBox)findViewById(R.id.cbPudding);
        cbRedBean=(CheckBox)findViewById(R.id.cbRedBean);
        cbCoconut=(CheckBox)findViewById(R.id.cbCoconut);
        btnMinus=(Button)findViewById(R.id.btnMinus);
        btnPlus=(Button)findViewById(R.id.btnPlus);
        txtQty=(TextView)findViewById(R.id.txtQty);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnReset=(Button)findViewById(R.id.btnReset);
        rvOrder=(RecyclerView)findViewById(R.id.rvOrder);
        txtName=(TextView)findViewById(R.id.txtName);
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        adapter=new OrderAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                index=posisi; String s[]=arrOrder.get(posisi).getType().split(" ");
                if (s[1]=="Tea") {
                    rbTea.setChecked(true);
                }
                else if (s[1]=="Coffee") {
                    rbCoffee.setChecked(true);
                }
                else if (s[1]=="Smoothies") {
                    rbSmoothies.setChecked(true);
                }
                cbPearl.toggle(); cbPudding.toggle(); cbRedBean.toggle(); cbCoconut.toggle();
                ArrayList<String>arrcb=arrOrder.get(posisi).getToppings();
                for (String a:arrcb) {
                    if (a=="Pearl") cbPearl.setChecked(true);
                    if (a=="Pudding") cbPudding.setChecked(true);
                    if (a=="RedBean") cbRedBean.setChecked(true);
                    if (a=="Coconut") cbCoconut.setChecked(true);
                } txtQty.setText(arrOrder.get(posisi).getQty()+"");
            }
        });
        rvOrder.setAdapter(adapter);
        total=0;
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t=Integer.valueOf(txtQty.getText().toString());
                if(t>1) {
                    t -= 1; txtQty.setText(""+t);
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t=Integer.valueOf(txtQty.getText().toString());
                t+=1; txtQty.setText(""+t);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rb; ArrayList<String>arrcb=new ArrayList<>();
                if (edName.getText().toString().length()>0){
                    if(rbTea.isChecked()==true){
                        rb="Tea"; total+=23000;
                    } else if (rbCoffee.isChecked()==true){
                        rb="Coffee"; total+=25000;
                    } else {
                        rb="Smoothies"; total+=30000;
                    }

                    if (cbPearl.isChecked()) {
                        arrcb.add("Pearl"); total+=3000;
                    }
                    if (cbPudding.isChecked()) {
                        arrcb.add("Pudding"); total+=3000;
                    }
                    if (cbRedBean.isChecked()) {
                        arrcb.add("Red Bean"); total+=4000;
                    }
                    if (cbCoconut.isChecked()) {
                        arrcb.add("Coconut"); total+=4000;
                    }

                    Integer t_subt=Integer.parseInt(txtTotal.getText().toString());
                    total+=total*Integer.parseInt(txtQty.getText().toString()); t_subt+= (int) total;
                    txtName.setText("Hi, "+edName.getText()+"!"); txtTotal.setText(t_subt.toString());
                    arrOrder.add(new Order(Integer.parseInt(txtQty.getText().toString())+" "+rb,
                            arrcb, Integer.parseInt(txtQty.getText().toString()),total));

                    RecyclerView.LayoutManager lm=new GridLayoutManager(MainActivity.this,1);
                    rvOrder.setLayoutManager(lm);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Field Name cannot be empty");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    alertDialog.show();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrOrder.clear(); adapter.notifyDataSetChanged();
                if (cbPearl.isChecked()) {
                    cbPearl.toggle();
                }
                if (cbPudding.isChecked()) {
                    cbPudding.toggle();
                }
                if (cbRedBean.isChecked()) {
                    cbRedBean.toggle();
                }
                if (cbCoconut.isChecked()) {
                    cbCoconut.toggle();
                }
                rgType.clearCheck(); txtQty.setText("1"); edName.setText("");
                txtName.setText("Hi, Cust!"); txtTotal.setText("0");
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index!=-1){
                    int t=Integer.parseInt(txtTotal.getText().toString());
                    t-=arrOrder.get(index).getSubtotal();
                    txtTotal.setText(t+"");
                    arrOrder.remove(index);
                    adapter.notifyDataSetChanged();
                    txtQty.setText("1");
                    ((RadioButton)rgType.getChildAt(0)).setChecked(true);
                    if (cbPearl.isChecked()) {
                        cbPearl.toggle();
                    }
                    if (cbPudding.isChecked()) {
                        cbPudding.toggle();
                    }
                    if (cbRedBean.isChecked()) {
                        cbRedBean.toggle();
                    }
                    if (cbCoconut.isChecked()) {
                        cbCoconut.toggle();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setMessage("Pilih Item yang akan di Delete");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    alertDialog.show();
                }
            }
        });
        rvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }
}
