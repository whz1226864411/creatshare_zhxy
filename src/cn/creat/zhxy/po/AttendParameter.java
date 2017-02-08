package cn.creat.zhxy.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cn.creat.zhxy.validgrop.AttendValidGropD;
import cn.creat.zhxy.validgrop.AttendValidGropN;

public class AttendParameter {
	@NotNull(message="{attend.start.notnull}",groups={AttendValidGropD.class})
	@Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}",message="{attend.date.notright}",groups={AttendValidGropD.class})
	private String start;
	@NotNull(message="{attend.end.notnull}",groups={AttendValidGropD.class})
	@Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}",message="{attend.date.notright}",groups={AttendValidGropD.class})
	private String end;
	@NotNull(message="{attend.page.notnull}",groups={AttendValidGropD.class,AttendValidGropN.class})
	private Integer page;
	@NotNull(message="{attend.rows.notnull}",groups={AttendValidGropD.class,AttendValidGropN.class})
	private Integer rows;
	private int[] flag;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int[] getFlag() {
		return flag;
	}
	public void setFlag(int[] flag) {
		this.flag = flag;
	}
}
