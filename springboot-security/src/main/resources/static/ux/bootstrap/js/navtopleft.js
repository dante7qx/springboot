var MainPage = {
		iframes: {},
		$ifrmContainer: '',
		$tabBar: '',
        $leftMenu: '',
        leftMenuHeight: '',
        
        init: function(){
        	this.$ifrmContainer = $('#js-iframe-container'),
    		this.$tabBar = $('#js-tabBar');
            this.$leftMenu = $('#js-left-menu');
            this.leftMenuHeight = $(window).height() - this.$ifrmContainer.offset().top;
            this.$leftMenu.height(this.leftMenuHeight);
            this.$ifrmContainer.height(this.leftMenuHeight);
            
            this.registerEvt();
        },
        
        makeIframe: function(title, src) {
            if (this.iframes[title]) {
            	return this.iframes[title];
            }
            var $ifrm = $('<iframe/>'),
                $li;
            $ifrm.addClass('embed-responsive-item');
            $ifrm.attr('src', src);
            this.$ifrmContainer.append($ifrm);
            this.iframes[title] = $ifrm;
            this.makeTab(title);
            this.selectTab(title);
            return $ifrm;
        },
        
        makeTab: function(title) {
            var $li = $('<li role="presentation" data-title-bk="' + title + '"><a href="javascript:;">' + title + '<span class="badge">x</span></a></li>');
            this.$tabBar.append($li);
            // tab点击
            $li.on('click', function() {
            	MainPage.selectTab(title);
            });
            // 点击x关闭tab
            $li.find('.badge').on('click', function() {
                // 选中该tab的左兄弟
                var idx = $li.index(),
                    $tabs = MainPage.$tabBar.find('li');
                // 如果当前tab有左兄弟，就选中左兄弟，否则如果有右兄弟则选中右兄弟
                idx > 0 ? MainPage.selectTab($tabs.eq(idx - 1).data('title-bk')) :
                    $tabs.length > idx + 1 && MainPage.selectTab($tabs.eq(idx + 1).data('title-bk'))
                MainPage.iframes[title].remove();
                $li.remove();
                delete MainPage.iframes[title];
            });
        },
        
        selectTab: function(title) {
            var $li = this.$tabBar.find('li[data-title-bk=' + title + ']');
            // 重置所有tab状态
            this.$tabBar.find('li').each(function(idx, li) {
                $(li).removeClass('active');
                $(li).find('.badge').css('display', 'none');
            });
            // 设置当前tab为活动页
            $li.addClass('active');
            $li.find('.badge').css('display', 'inline-block');
            $.each(MainPage.iframes, function(key, $ifrm) {
                $ifrm.css('display', key === title ? 'block' : 'none');
            });
        },
        
        hideLeftMenu: function() {
        	$('#js-left-menu').hide();
            $('#js-main-box').css('width', '100%');
        },
        
        showLeftMenu: function() {
            $('#js-left-menu').show();
            $('#js-main-box').css('width', '');
        },
        
        noSecondaryMenuBindClick: function() {
            $('[data-src]').off('click');
            $('[data-src]').on('click', function() {
                var $this = $(this),
                    title = $this.data('title'),
                    src = $this.data('src');
                MainPage.makeIframe(title, src);
                MainPage.selectTab(title);

                // 如果是左侧菜单的子菜单，则不隐藏左菜单
                if (!MainPage.$leftMenu.find('.nav').has('[data-title=' + $(this).data('title') + ']').length) {
                	MainPage.hideLeftMenu();
                }
            });
        },
        
        registerEvt: function() {
        	// 顶部包含二级菜单的一级菜单
            $('.level1-menu').on('click', function() {
                var $list = $(this).find('ul>li');
                MainPage.$leftMenu.find('ul').empty().append($list.clone());
                MainPage.$leftMenu.removeClass('left-menu_shrink');
                MainPage.showLeftMenu();
                // 重新绑定事件
                MainPage.noSecondaryMenuBindClick();
            });
            
            // 左侧菜单箭头
            $('#js-lmenu-arrow').on('click', function() {
                $('#js-left-menu').toggleClass('left-menu_shrink');
            });
            
            MainPage.noSecondaryMenuBindClick();
        }
};