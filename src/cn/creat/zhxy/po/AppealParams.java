package cn.creat.zhxy.po;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppealParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8293024704690659597L;
	@NotNull(message="{appeal.class_No.notnull}")
	private String class_No;
	@NotNull(message="{appeal.s_Date.notnull}")
	private String s_Date;
	@NotNull(message="{appeal.jc.notnull}")
	private String jc;
	@NotNull(message="{appeal.s_Code.notnull}")
	private String s_Code;
	@NotNull(message="{appeal.r_BH.notnull}")
	private String r_BH;
	@NotNull(message="{appeal.term.notnull}")
	private String term;
	@NotNull(message="{appeal.remark.notnull}")
	@Size(min=6,message="{appeal.remark.length}")
	private String remark;
	@NotNull(message="{appeal.s_Status.notnull}")
	private String s_Status;
	@NotNull(message="{appeal.a_Status.notnull}")
	private String a_Status;
	public String getClass_No() {
		return class_No;
	}
	public void setClass_No(String class_No) {
		this.class_No = class_No;
	}
	public String getS_Date() {
		return s_Date;
	}
	public void setS_Date(String s_Date) {
		this.s_Date = s_Date;
	}
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc;
	}
	public String getR_BH() {
		return r_BH;
	}
	public void setR_BH(String r_BH) {
		this.r_BH = r_BH;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getS_Status() {
		return s_Status;
	}
	public void setS_Status(String s_Status) {
		this.s_Status = s_Status;
	}
	public String getA_Status() {
		return a_Status;
	}
	public void setA_Status(String a_Status) {
		this.a_Status = a_Status;
	}
	public String getS_Code() {
		return s_Code;
	}
	public void setS_Code(String s_Code) {
		this.s_Code = s_Code;
	}
	
}
