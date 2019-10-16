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
	
	var myChart = echarts.init(document.getElementById('#tubiao'));
	var option = {
			title : {
				text: '',
				subtext : ''
			},
			tooltip : {
				trigger: 'axis'
			},
			legend: {
				data: ['已签到','未签到']
			},
			toolbox : {
				show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
			},
			calculable : true,
			xAxis : [
				{
					type : 'category',
					data : []
				}
			],
			yAxis : [
				{
					type : 'value'
				}
			],
			series : [
				{
					name:'已签到',
					type: 'bar',
					data: [],
					markPoint : {
						data : [
							{type : 'max', name : '最大值'},
							{type : 'min', name : '最小值'}
						]
					},
					markLine : {
						data : [
							{type : 'average', name : '平均值'}
						]
					}
				},
				{
    	            name:'未签到',
    	            type:'bar',
    	            data:[0, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
    	            markPoint : {
    	                data : [
    	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
    	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 6}
    	                ]
    	            }
				}
			]
	};
	myChart.setOption(option);
	$.get('').done(function(data) {
		myChart.setOption({
            series: [{
                // 根据名字对应到相应的系列
                name: '未签到',
                data:data.weichengjiao
            },
            {
                // 根据名字对应到相应的系列
                name: '已签到',
                data: data.chengjiao
            }
            ]
        });
	});
 })


