
		var $;
		layui.config({
			base : "js/"
		}).use(['form','layer','jquery','upload','laydate'],function(){
			var form = layui.form,
				layer = parent.layer === undefined ? layui.layer : parent.layer,
				upload = layui.upload,
				laypage = layui.laypage,
				$ = layui.jquery
				,laydate = layui.laydate;
			$.get("getTeacherArrlist", function(data){
				var list=data.list;
	    		for(var i=0;i<list.length;i++){
	    			$("#tcsuid").append("<option value='"+list[i].id+"'>"+list[i].id+"</option>");
	    		}
	    		form.render();//必须要再次渲染，要不然option显示不出来
			});
			$.get("getClassinfolist", function(data){
				var list=data.list;
	    		for(var i=0;i<list.length;i++){
	    			$("#classid").append("<option  value='"+list[i].id+"'>"+list[i].nickname+"</option>");
	    		}
	    		form.render();//必须要再次渲染，要不然option显示不出来
			});
			//===========================================
				//普通图片上传
				  var uploadInst = upload.render({
				    elem: '#upschool'
				    ,url: '/admin/faceLogin'
				    ,multiple:false
				    ,data: {
				    	week: function(){
				    	    return $('#week').val();
				    	  },
				    	  classid: function(){
				    	    return $('#classid').val();
				    	  },
				    	  tcsuid: function(){
				    	    return $('#tcsuid').val();
				    	  },
				  
				    	}
				    ,before: function(obj){
				      //预读本地文件示例，不支持ie8
				      obj.preview(function(index, file, result){
				    	  $('#demo1').attr('src', result); //图片链接（base64）
				      });
				    }
				    ,done: function(res){
				      //如果上传失败
				      if(res.code > 0){
				        return layer.msg('上传失败:'+res.msg);
				      }else{
				    	  $('#img').val(res.src);
				    	  $('#age').text(res.age);
				    	  $('#beauty').text(res.beauty);
				    	  $('#type').text(res.type);
				      }
				      //上传成功
				    }
				    ,error: function(){
				      //演示失败状态，并实现重传
				      var demoText = $('#demoText');
				      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
				      demoText.find('.demo-reload').on('click', function(){
				        uploadInst.upload();
				      });
				    }
				  });
				
		 	form.on("submit(add)",function(data){
		 		var index;
		 		 $.ajax({//异步请求返回给后台
			    	  url:'saveUser',
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