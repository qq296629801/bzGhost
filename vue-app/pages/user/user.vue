<template>
	<view>
		<u-navbar :is-back="false" title="　" :border-bottom="false">
			<view class="u-flex u-row-right" style="width: 100%;">
				<view class="camera u-flex u-row-center">
					<u-icon @tap="jump('pages/find/firendCircle')" name="camera-fill" color="#000000" size="40"></u-icon>
				</view>
			</view>
		</u-navbar>
		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-b-30">
			<view class="u-m-r-10 u-p-r-30">
				<u-avatar @tap="previewImage" mode="square" src="/static/image/huge.jpg" size="140"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-20">{{user.realname}}</view>
				<view class="u-font-14 u-tips-color">{{user.money}}</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="arrow-right" color="#969799" size="28" @tap="jump('pages/user/my')"></u-icon>
			</view>
		</view>
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item @tap="jump(item.url)" v-for="(item, index) in list" :key="index" :title="item.title" :title-style="{ marginLeft: '30rpx' ,fontWeight:'400'}">
					<u-icon slot="icon" :name="item.icon" :color="item.color" size="40"></u-icon>
				</u-cell-item>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	import { mapState, mapMutations} from 'vuex';
	export default {
		data() {
			return {
				show:true,
				list: [
					{
						title: '朋友圈',
						color: '#00aaff',
						icon: 'photo-fill',
						url:'pages/find/firendCircle'
					},
					{
						title: '设置',
						color: '#00aaff',
						icon: 'setting-fill',
						url:'pages/seting/index'
					}
				],
			}
		},
		onLoad() {
		},
		computed: {
			...mapState(['user','chatObj'])
		},
		methods: {
			jump(url){
				this.$u.route({
					url:url
				})
			},
			previewImage() {
				let current = '/static/image/girl.jpg'
				uni.previewImage({
					current,
					urls: [current],
					// #ifndef MP-WEIXIN
					indicator: 'number'
					// #endif
				});
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color: #F6F7F8;
}

.camera{
	width: 54px;
	height: 44px;
	
	&:active{
		background-color: #ededed;
	}
}
.user-box{
	background-color: #fff;
}
</style>
