package com.sj.yinjiaoyun.shituwang.xmppmanager;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.event.EmptyEvent;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.StreamError;

import java.util.Timer;
import java.util.TimerTask;

import me.xiaopan.android.net.NetworkUtils;


/**
 * @author baiyuliang
 * 监听xmpp连接状态的监听器
 */
public class CheckConnectionListener implements ConnectionListener {
	private String mAccount;
	private String mPassword;
	private Timer tExit;
	private TimerTask timerTask;
	private MyChatManagerListener chatManagerListener;
	public CheckConnectionListener(Context context,MyChatManagerListener chatManagerListener){
		this.chatManagerListener=chatManagerListener;
		//标示登录省份(1是学生2是老师)
		int loginId = PreferencesUtils.getSharePreInt(context, Const.LOGINID);
		//登录的id
	    int id = PreferencesUtils.getSharePreInt(context, Const.ID);
		if (loginId == 1) {//学生登录
			mAccount="5stu_"+id;
		} else if (loginId == 2) {//老师登录
			mAccount="5tch_"+id;
		}
		mPassword = PreferencesUtils.getSharePreStr(context, Const.PWD);
	}

	@Override
	public void connectionClosed() {
		Logger.d("TaxiConnectionListener", "连接关闭");
	}




	@Override
	public void connectionClosedOnError(Exception e) {
			Logger.d("TaxiConnectionListener", "连接关闭异常");
			if(e instanceof XMPPException){
				XMPPException xe = (XMPPException) e;
				final StreamError error = xe.getStreamError();
				String errorCode;
				if (error!=null) {
					errorCode = error.getCode();// larosn 0930
					Logger.d("TaxiConnectionListener", "连接断开，错误码" + errorCode);
					if (errorCode.equalsIgnoreCase("conflict")) {// 被踢下线
						EventBus.getDefault().post(new EmptyEvent(true));
						return;
					}
				}
			}
			/**
			 * 断线重连
			 */
			if(tExit!=null){
				if(timerTask!=null){
					timerTask.cancel();
					timerTask=null;
				}
			}
			tExit=new Timer();
			timerTask=new ConnectTask();
			tExit.schedule(timerTask,2000);

		}

	@Override
	public void reconnectingIn(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 断线重新连接
	 */
	public void reconnect() {
		if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
			if(timerTask!=null){
				timerTask.cancel();
				timerTask=null;
			}
			return;
		}
		if(MyApplication.xmppConnection!=null){
			MyApplication.xmppConnection.disconnect();
			HeartbeatPackage.isRunning=false;
			MyApplication.xmppConnection=null;
		}
		MyApplication.xmppConnection= XmppConnectionManager.OnLogin(mAccount,mPassword,this,chatManagerListener);
		if(MyApplication.xmppConnection.isConnected()){
			Logger.d("重连成功");
			if(MyApplication.xmppConnection.isAuthenticated()){
				//添加心跳包
				HeartbeatPackage.newInstens();
			}else {
				reconnect();
			}
		}else {
			reconnect();
		}
		Logger.d("断线重连");
	}

	class ConnectTask extends TimerTask{

		@Override
		public void run() {
			reconnect();
		}
	}


}
