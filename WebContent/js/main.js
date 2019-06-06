layui.config({
	base : "js/"
}).use(['form','element','layer','jquery','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element,
		table = layui.table,
		$ = layui.jquery;

	$(".layui-col-md3 a").on("click",function(){
		window.parent.addTab($(this));
	});
	

  // 基于准备好的dom，初始化echarts实例
//    var myChart = echarts.init(document.getElementById('tubiao'));
//    var option = {
//    	    title : {
//    	        text: '用户成交量和未成交量',
//    	        subtext: '2019年数据'
//    	    },
//    	    tooltip : {
//    	        trigger: 'axis'
//    	    },
//    	    legend: {
//    	        data:['已成交','未成交']
//    	    },
//    	    toolbox: {
//    	        show : true,
//    	        feature : {
//    	            mark : {show: true},
//    	            dataView : {show: true, readOnly: false},
//    	            magicType : {show: true, type: ['line', 'bar']},
//    	            restore : {show: true},
//    	            saveAsImage : {show: true}
//    	        }
//    	    },
//    	    calculable : true,
//    	    xAxis : [
//    	        {
//    	            type : 'category',
//    	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
//    	        }
//    	    ],
//    	    yAxis : [
//    	        {
//    	            type : 'value'
//    	        }
//    	    ],
//    	    series : [
//    	        {
//    	            name:'已成交',
//    	            type:'bar',
//    	            data:[],
//    	            markPoint : {
//    	                data : [
//    	                    {type : 'max', name: '最大值'},
//    	                    {type : 'min', name: '最小值'}
//    	                ]
//    	            },
//    	            
//    	            markLine : {
//    	                data : [
//    	                    {type : 'average', name: '平均值'}
//    	                ]
//    	            
//    	            }
//    	        },
//    	        {
//    	            name:'未成交',
//    	            type:'bar',
//    	            data:[0, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
//    	            markPoint : {
//    	                data : [
//    	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
//    	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 6}
//    	                ]
//    	            },
//    	            markLine : {
//    	                data : [
//    	                    {type : 'average', name : '平均值'}
//    	                ]
//    	            }
//    	        }
//    	    ]
//    	};
//    myChart.setOption(option);
//   // 异步加载数据
//    $.get('getTubiaoinfo').done(function (data) {
//        //设置数据
//        myChart.setOption({
//            series: [{
//                // 根据名字对应到相应的系列
//                name: '未成交',
//                data:data.weichengjiao
//            },
//            {
//                // 根据名字对应到相应的系列
//                name: '已成交',
//                data: data.chengjiao
//            }
//            ]
//        });
//        });
 })


