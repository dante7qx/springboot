var MainPage = {
		iframes: {},
		$ifrmContainer: '',
		$tabBar: '',
		init: function() {
			this.$ifrmContainer = $('#js-iframe-container');
			this.$tabBar = $('#js-tabBar');
			this.$ifrmContainer.height($(window).height() - this.$ifrmContainer.offset().top - 60);
			this.registerEvt();
		},
		registerEvt: function() {
			 $('[data-src]').on('click', function() {
	                var $this = $(this),
	                    title = $this.data('title'),
	                    src = $this.data('src');
	                MainPage.makeIframe(title, src);
	                MainPage.selectTab(title);
             });
			 $('.menu-item').on('mouseover', function() {
				 $(this).find('>.sub-menu').css('display', 'block');
             }).on('mouseout', function() {
            	 $(this).find('>.sub-menu').css('display', 'none');
             }).on('click', function() {
            	 $(this).find('>.sub-menu').css('display', 'none');
             });
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
            var $li = $('<li role="presentation" data-title="' + title + '"><a href="#">' + title + '<span class="badge">x</span></a></li>');
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
                idx > 0 ? MainPage.selectTab($tabs.eq(idx - 1).data('title')) :
                    $tabs.length > idx + 1 && MainPage.selectTab($tabs.eq(idx + 1).data('title'))
                MainPage.iframes[title].remove();
                $li.remove();
                delete MainPage.iframes[title];
            });
        },
        selectTab: function(title) {
            var $li = this.$tabBar.find('li[data-title=' + title + ']');
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
        }
};