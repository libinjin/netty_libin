            
			
			
			
			
			  private int id;
    private int userId;
    private String nickName;
    private String label;
    private int rank;
    private String desc;
    private String button;
    private String forwardUrl;
    private int used;
    private Date createTime;
    private Date updateTime;
    private int focusPoint;
    private String focusStr;
    private String focusValue;
    private int forwardType;
    private int type;

			
	mygrid.setHeader("序号,#master_checkbox,用户id,昵称,甜橙牛人类型,牛人标签,关注点,排序,牛人描述,按钮文字,跳转类型,跳转地址,是否生效,创建时间,操作");

			
			String xml = RenderUtil.generateNewXML(pageHolder,
			new String[]{"", "", "userId", "nickName", "type", "label", "focusStr", "rank","desc", 
			"button", "forwardType", "forwardUrl", "used", "createTime",""}, null);
