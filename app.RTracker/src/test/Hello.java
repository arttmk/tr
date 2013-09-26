package test;

import org.apache.click.control.FileField;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

public class Hello extends org.apache.click.Page {
	private static final String APP_LOG			= "Hello";
	private static final String APP_NAME			= "テストもろもろ";

	  private Form form1;
	  private Form form2;
	  private Form form3;
	  private Form form4;

	  private TextField field1;
	  private Submit submit1;
	  private String msg = "";

	  private Submit submit2;
	  private String msg2 = "";

	  private Submit submit3;
	  private Submit submit4;

	  private FileField fileField;


	  public Hello(){


	    this.addModel("title", "Click Sample");
	    this.msg = "何か書いて！";
	    form1 = new Form("form1");
	    field1 = new TextField("field1","入力：");
	    submit1 = new Submit("submit1","送信",this,"sendNow");
	    form1.add(field1);
	    form1.add(submit1);
	    //this.addControl(form1);




	    this.addControl(form1);




	  }

	  @Override
	  public void onRender() {
	    this.addModel("msg", this.msg);
	    this.addModel("msg2", this.msg2);


	    super.onRender();
	  }
	  
	  public boolean sendNow(){
	    this.msg = "あなたは、" + field1.getValue() + "と書きました。";
	    return true;
	  }


}