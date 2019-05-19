var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery', 'laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery
		,laydate = layui.laydate;
	  
	  //日期
	  laydate.render({
	    elem: '#date'
	  });
	  
	  laydate.render({
		    elem: '#data'
		  });
		  
		var arr=new Array();
        
        for(var i=100;i<105;i++){
        	var c="c"+i;
	        arr.push("<input type='checkbox' name='"+c+"' lay-skin='primary'  title='"+c+"'>");
        	
        }	        
        $("#permission").append(arr.join("\n"));
		form.render();//必须要再次渲染，要不然option显示不出来
		
	//===========================================
		//学校的下拉框
		$.get("getUserlist", function(data){
			var list=data.list;
			for(var i=0;i<list.length;i++){
        		$("#user_id").append("<option value='"+list[i].id+"'>"+list[i].username+"</option>");
			}
			form.render();//必须要再次渲染，要不然option显示不出来
		});
		
		
 	form.on("submit(add)",function(data){
 		var index;
 		 $.ajax({//异步请求返回给后台
	    	  url:'saveNews',
	    	  type:'POST',
	    	  data:data.field,
	    	  dataType:'json',
	    	  beforeSend: function(re){
	    		  index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	          },
	    	  success:function(d){
	    			//弹出loading
			    	top.layer.close(index);
			  		top.layer.msg("添加成功！");
			   		layer.closeAll("iframe");
			  	 		//刷新父页面
			  	 	parent.location.reload();
		    		
	    	  },
	    	  error:function(XMLHttpRequest, textStatus, errorThrown){
	    		  top.layer.msg('保存失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
	    		        time: 20000, //20s后自动关闭
	    		        btn: ['知道了']
	    		      });
	           }
	      });
 		return false;
 	})
	
})