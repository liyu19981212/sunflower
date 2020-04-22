$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    })
    //生成用户数据
    $('#mytab').bootstrapTable({
    	method: 'get',
		contentType: "application/json;charset=utf-8",
		datatype: 'json',
		url:"/admin/getTeacherList",
    	//height:tableHeight(),//高度调整
    	toolbar: '#toolbar',
    	striped: true, //是否显示行间隔色
    	dataField: "res",
    	pageNumber: 1, //初始化加载第一页，默认第一页
    	pagination:true,//是否分页
		search: true,
    	sidePagination:'client',
    	pageSize:5,//单页记录数
    	pageList:[5,10,20,30],//分页步进值
    	showRefresh:true,//刷新按钮
    	showColumns:true,
    	clickToSelect: true,//是否启用点击选中行
    	toolbarAlign:'right',
    	buttonsAlign:'right',//按钮对齐方式
    	toolbar:'#toolbar',//指定工作栏
    	columns:[
        	{
        		title:'全选',
        		field:'select',
        		checkbox:true,
        		width:25,
        		align:'center',
        		valign:'middle'
        	},
        	{
        		title:'t_id',
        		field:'t_id',
        		visible:false
        	},
        	{
        		title:'登录名',
        		field:'username',
        		sortable:true
        	},
        	{
        		title:'姓名',
        		field:'t_name',
        		sortable:true
        	},
        	{
        		title:'手机号',
        		field:'telphone',
        	},
        	{
        		title:'工号',
        		field:'t_num'
        	},
        	{
        		title:'密码',
        		field:'password',
        		sortable:true
        	}

    	],
    	locale:'zh-CN',//中文支持,
    })
    /*
     * 用户管理首页事件
     */

		  //请求成功后生成增加用户页面表单内容
		  $('#addForm').bootstrapValidator({
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
		           fields: {
		               username: {
		                   validators: {
		                       notEmpty: {
		                           message: '登录名不能为空'
		                       },
		                       stringLength:{
		               			min:5,
		               			max:15,
		               			message:'登录名为5-10位'
		               		}
		                   }
		               },
		               t_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '姓名不能为空'
		                       },
		               		stringLength:{
		               			min:2,
		               			max:10,
		               			message:'姓名为2-10位'
		               		}
		                   }
		               },

		               password:{
		               	validators:{
		               		notEmpty:{
		               			message:'密码不能为空'
		               		},
		                       stringLength:{
		                       	min:6,
		                       	max:128,
		                       	message:'密码为6-128位'
		                       }
		               	}

		               },
		               telphone: {
		                   validators: {
		                       notEmpty: {
		                           message: '手机号不能为空'
		                       },
		                       stringLength: {
		                           min: 11,
		                           max: 11,
		                           message: '手机号必须为11位'
		                       },
		                       regexp: {
		                           regexp: /^1(3|4|5|7|8)\d{9}$/,
		                           message: '请填写正确的手机号'
		                       }
		                   }
		               },

		           }
		     });
	$('#editForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			username: {
				validators: {
					notEmpty: {
						message: '登录名不能为空'
					},
					stringLength:{
						min:5,
						max:15,
						message:'登录名为5-10位'
					}
				}
			},
			t_name: {
				validators: {
					notEmpty: {
						message: '姓名不能为空'
					},
					stringLength:{
						min:2,
						max:10,
						message:'姓名为2-10位'
					}
				}
			},

			password:{
				validators:{
					notEmpty:{
						message:'密码不能为空'
					},
					stringLength:{
						min:6,
						max:128,
						message:'密码为6-128位'
					}
				}

			},
			telphone: {
				validators: {
					notEmpty: {
						message: '手机号不能为空'
					},
					stringLength: {
						min: 11,
						max: 11,
						message: '手机号必须为11位'
					},
					regexp: {
						regexp: /^1(3|4|5|7|8)\d{9}$/,
						message: '请填写正确的手机号'
					}
				}
			},

		}
	});




    //查询按钮事件
    $('#search_btn').click(function(){
    	$('#mytab').bootstrapTable('refresh', {url: '../index.php/admin/index/userManagement'});
    })
    
    //增加按钮事件
    $('#btn_add').click(function(){
		$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
		$('.addBody').css('display','block');
		$('.addBody').addClass('animated slideInRight');
    })
    //删除按钮与修改按钮的出现与消失
    $('.bootstrap-table').change(function(){
    	var dataArr=$('#mytab .selected');
    	if(dataArr.length==1){
    		$('#btn_edit').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
    	}else{
    		$('#btn_edit').addClass('fadeOutRight');
    		setTimeout(function(){
    			$('#btn_edit').css('display','none');
    		},400);	
    	}
    	if(dataArr.length>=1){
    		$('#btn_delete').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
    	}else{
    		$('#btn_delete').addClass('fadeOutRight');
    		setTimeout(function(){
    			$('#btn_delete').css('display','none');
    		},400);	
    	}
    });
    //修改按钮事件
    $('#btn_edit').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_t_id').val(dataArr[0].t_id);
		$('#edit_username').val(dataArr[0].username);
		$('#edit_t_name').val(dataArr[0].t_name);
		$('#edit_telphone').val(dataArr[0].telphone);
		$('#edit_password').val(dataArr[0].password);
		$('#edit_t_num').val(dataArr[0].t_num);

		//先清空角色复选框
	    $('#editForm .edit input').prop('checked',false);
		//获取用户角色

    })
    /*
     * 用户管理增加用户页面所有事件
    */
    //增加页面表单验证   
    // Validate the form manually
	$('#add_saveBtn').click(function() {
		//点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		//如果表单验证正确，则请求后台添加用户
		if($("#addForm").data('bootstrapValidator').isValid()){

			var formObject = {};
			var formArray =$("#addForm").serializeArray();
			$.each(formArray,function(i,item){
				formObject[item.name] = item.value;
			});
			alert(JSON.stringify(formObject));
			$.ajax({
					type: 'POST',
					url: '/admin/addTeacher',
					headers: {"Content-type":"application/json; charset=UTF-8"},
					ContentType: "application/json",
					dataType: "json",
					data:JSON.stringify(formObject),


					success: function (result) {
						//后台返回添加成功

						alert("成功")
						if(result.Status="ok"){
							$('.addBody').addClass('animated slideOutLeft');
							setTimeout(function(){
								$('.addBody').removeClass('animated slideOutLeft').css('display','none');
							},500);
							$('.tableBody').css('display','block').addClass('animated slideInRight');
							$('#mytab').bootstrapTable('refresh', {url: '/admin/getTeacherList'});
							$('#addForm').data('bootstrapValidator').resetForm(true);
							//隐藏修改与删除按钮
							$('#btn_delete').css('display','none');
							$('#btn_edit').css('display','none');
						}
					}

				}
			)


		}
	})
    //增加页面返回按钮事件
    $('#add_backBtn').click(function() {
    	$('.addBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.addBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
    	$('.tableBody').css('display','block').addClass('animated slideInRight');  
    	$('#addForm').data('bootstrapValidator').resetForm(true);
    });
    /*
     * 用户管理修改用户页面所有事件
    */
    //修改页面回退按钮事件
    $('#edit_backBtn').click(function(){
    	$('.changeBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
		},500)
    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
    	$('#editForm').data('bootstrapValidator').resetForm(true);
    })
    //修改页面保存按钮事件
	$('#edit_saveBtn').click(function(){
		$('#editForm').bootstrapValidator('validate');
		if($("#editForm").data('bootstrapValidator').isValid()){
			var formObject = {};
			var formArray =$("#editForm").serializeArray();
			$.each(formArray,function(i,item){
				formObject[item.name] = item.value;
			});
			$.ajax({
				type: 'PUT',
				url: '/admin/updateTeacher',
				headers: {"Content-type":"application/json; charset=UTF-8"},
				ContentType: "application/json",
				dataType: "json",
				data:JSON.stringify(formObject),
				success:function(result) {
					if (result.Status = "ok") {
						//隐藏修改与删除按钮
						alert("修改成功")
						$('#btn_delete').css('display', 'none');
						$('#btn_edit').css('display', 'none');
						//回退到人员管理主页
						$('.changeBody').addClass('animated slideOutLeft');
						setTimeout(function () {
							$('.changeBody').removeClass('animated slideOutLeft').css('display', 'none');
						}, 500)
						$('.tableBody').css('display', 'block').addClass('animated slideInRight');
						//刷新人员管理主页
						$('#mytab').bootstrapTable('refresh', {url: '/admin/getTeacherList'});
						//修改页面表单重置
						$('#editForm').data('bootstrapValidator').resetForm(true);
					}
				}
			})


		}
	})
	//删除事件按钮
	$('#btn_delete').click(function(){
		var dataArr=$('#mytab').bootstrapTable('getSelections');
		$('.popup_de .show_msg').text('确定要删除该用户吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click',function(){
			var t_id=dataArr[0].t_id;
			$.ajax({
				type: 'DELETE',
				dataType: 'json',
				//data: {"id" : n_id},
				url: '/admin/deleteTeacher/'+t_id,
				async: false, //加上之后不在跳转进error
				success: function(data) {

					$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					})
					$('#mytab').bootstrapTable('refresh', {url: '/admin/getTeacherList'});


				},
				error: function(data) {
					alert(data);
				}
			})

		})
	})
    //弹出框取消按钮事件
   　　$('.popup_de .btn_cancel').click(function(){
	   $('.popup_de').removeClass('bbox');
   　　})
    //弹出框关闭按钮事件
     $('.popup_de .popup_close').click(function(){
	   $('.popup_de').removeClass('bbox');
   　　})
})
function tableHeight() {
    return $(window).height() - 140;
}
