<template>
	<view>
		<u-index-list :scrollTop="scrollTop" :index-list="indexList" :active-color="'#3CC51F'">
			<view v-if="isShowMenu">
				<u-index-anchor active-color="#1fb6f1" index="#" />
				<view class="list-cell" hover-class="message-hover-class" @tap="linkToNewFriend">
					<u-image width="70rpx" height="70rpx" src="/static/image/friend_1.png"></u-image>
					<view class="list-cell-name u-font-16">新的朋友</view>
				</view>
				<view class="list-cell " hover-class="message-hover-class"  @tap="linkToGroupItem">
					<u-image width="70rpx" height="70rpx" src="/static/image/group_1.png"></u-image>
					<view  class="list-cell-name u-font-16">我的群聊</view>
				</view>
			</view>
			
			<view v-for="(item, index) in list" :key="index">
				<u-index-anchor :index="item.name" v-if="item.members&&item.members.length"/>
				<view v-for="user in item.members" :key="user.id"  class="list-cell " @tap="toCard(user)" hover-class="message-hover-class">
					<image :src="host + user.avatar"></image>
					<view  class="list-cell-name u-font-16">{{user.nickName}}</view>
				</view>
			</view>
		</u-index-list>
	</view>
</template>

<script>
	import base from '@/util/baseUrl.js';
	export default {
		name:'addressBook',
		props:{
			list:{
				type:Object,
				default () {
					return {};
				}
			},
			isShowMenu:{
				type: Boolean,
				default () {
					return false;
				}
			},
			isSearch:{
				type: Boolean,
				default () {
					return false;
				}
			},
			scrollTop:{
				type:Number,
				default: 0
			}
		},
		watch:{
			keyword:function(val){
				let arr = this.oldList;
				if(val!=''){
					this.list = arr.filter(v => {
						let flag = false
						if(v.members.length>0){
							v.members.forEach(m=>{
								if(m.groupNickName.includes(val)){
									flag = true
								}
							})
						}
						return flag
					})
				}else {
					this.list = this.oldList
				}
			}
		},
		data() {
			return {
				oldList: this.list,
				keyword:'',
				host: base.webUrl,
				indexList: ['#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
			};
		},
		methods:{
			toCard(user){
				this.$emit('toCard',user);
			},
			linkToNewFriend(){
				this.$u.route({
					url: 'pages/user/newFriend'
				});
			},
			linkToGroupItem(){
				this.$u.route({
					url: 'pages/group/groupItem'
				});
			}
		}
	}
</script>

<style lang="scss">
.list-cell {
	display: flex;
	box-sizing: border-box;
	width: 100%;
	padding: 10px 24rpx;
	overflow: hidden;
	color: #323233;
	line-height: 48rpx;
	background-color: #fff;
	border-bottom: solid 3rpx #eeeeee;
	align-items: center;
	image {
		width: 76rpx;
		height: 76rpx;
		border-radius: 12rpx;
		flex: 0 0 76rpx;
	}
	&-name{
		padding-left: 20rpx;
	}
}
</style>
