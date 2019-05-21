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
		$.get("getStudentModel?id="+id, function(data){
			var m=data.m;
	        //执行加载数据的方法 
			$("input[name='no']").val(m.no);
	        var arr=new Array();
	        	if(m.sex==1){
		        	arr.push("<input type='radio' name='type' lay-skin='primary' value='1' checked=''  title='班干'>");
		        	arr.push("<input type='radio' name='type' lay-skin='primary' value='0' title='学生'>");
	        	}else{
	        		arr.push("<input type='radio' name='type' lay-skin='primary' value='0' checked=''  title='学生'>");
		        	arr.push("<input type='radio' name='type' lay-skin='primary' value='1' title='班干'>");
	        }
	        $("#mytype").append(arr.join("\n"));
	        form.render();//必须要再次渲染，要不然option显示不出来
			$.get("getClassinfolist", function(data){
				var list=data.list;
	    		for(var i=0;i<list.length;i++){
	    			if(list[i].id==m.clas){
	    				$("#clas").append("<option selected='true' value='"+list[i].id+"'>"+list[i].nickname+"</option>");
	    			}else{
	    				$("#clas").append("<option value='"+list[i].id+"'>"+list[i].nickname+"</option>");
	    			}
	    			}
	    		form.render();//必须要再次渲染，要不然option显示不出来
			});
			
		})

 	form.on("submit(update)",function(data){
 		var index;
 		 $.ajax({//异步请求返回给后台
	    	  url:'updateStudent',
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
