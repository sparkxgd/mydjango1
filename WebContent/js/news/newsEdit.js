var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;
		var id=$("input[name='id']").val();
		//加载页面数据
		$.get("getNewsModel?id="+id, function(data){
			var m=data.m;
//			var obj = $.parseJSON(m.permission);
	        //执行加载数据的方法
			$("input[name='title']").val(m.title);
			$("input[name='content']").val(m.content);
			$("input[name='user_id']").val(m.user_id);
			$("input[name='create_time']").val(m.create_time);
			$("input[name='release_time']").val(m.release_time);
			$("input[name='status']").val(m.status);
			$("input[name='reading']").val(m.reading);
			$.get("getSchoollist", function(data){
				var list=data.list;
				var id=list[0].id;
	    		for(var i=0;i<list.length;i++){
	    			if(list[i].id==m.school){
	    				$("#school").append("<option selected='true' value='"+list[i].id+"'>"+list[i].nickname+"</option>");
	    			}else{
	    				$("#school").append("<option value='"+list[i].id+"'>"+list[i].nickname+"</option>");
	    			}
	    			}
				form.render();//必须要再次渲染，要不然option显示不出来
			});
	        
//	        var arr=new Array();
//	        
//	        for(var i=100;i<105;i++){
//	        	var c="c"+i;
//	        	var v=obj[c];
//	        	if(v==1){
//		        	arr.push("<input type='checkbox' name='"+c+"' lay-skin='primary' checked=''  title='"+c+"'>");
//	        	}else{
//		        	arr.push("<input type='checkbox' name='"+c+"' lay-skin='primary' title='"+c+"'>");
//	        	}
//	        	
//	        }	        
//	        $("#permission").append(arr.join("\n"));
//			form.render();//必须要再次渲染，要不然option显示不出来
		})

 	form.on("submit(update)",function(data){
 		var index;
 		 $.ajax({//异步请求返回给后台
	    	  url:'updateNews',
	    	  type:'POST',
	    	  data:data.field,
	    	  dataType:'json',
	    	  beforeSend: function(re){
	    		  index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	          },
	    	  success:function(d){
	    			//弹出loading
			    	top.layer.close(index);
			  		top.layer.msg("操作成功！");
			   		layer.closeAll("iframe");
			  	 		//刷新父页面
			  	 	parent.location.reload();
		    		
	    	  },
	    	  error:function(XMLHttpRequest, textStatus, errorThrown){
	    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
	    		        time: 20000, //20s后自动关闭
	    		        btn: ['知道了']
	    		      });
	           }
	      });
 		return false;
 	})
	
})
