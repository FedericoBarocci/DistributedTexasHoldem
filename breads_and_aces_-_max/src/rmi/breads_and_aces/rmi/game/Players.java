package breads_and_aces.rmi.game;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.utils.observatory.Observable;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class Players implements Observable<String> {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
	// flyweight: gof use static way
	private /*static*/ final Map<String, NodeConnectionInfo> playersNodeInfos = new LinkedHashMap<>();
	
	public void addPlayer(NodeConnectionInfo nodeConnectionInfo) {
		String id = nodeConnectionInfo.getId();
		playersNodeInfos.put(id, nodeConnectionInfo);
		notifyObservers(id);
	}
	
	@Override
	public void addObserver(Observer<String> observer) {
		observableDelegate.addObserver(observer);
	}

	@Override
	public void removeObserver(Observer<String> observer) {
		observableDelegate.removeObserver(observer);
	}

	@Override
	public void notifyObservers(String data) {
		observableDelegate.notifyObservers(data);		
	}
	
//	@Override
//	public void notifyObservers(Collection<String> data) {
//		
//	}
	
	public Map<String, NodeConnectionInfo> getPlayersNodeInfos() {
		return playersNodeInfos;
	}

	public void setPlayersNodeInfos(Map<String, NodeConnectionInfo> nodeConnectionInfos) {
		this.playersNodeInfos.clear();
		this.playersNodeInfos.putAll(nodeConnectionInfos);
		notifyObservers( nodeConnectionInfos.values().stream().map(NodeConnectionInfo::getId).collect(Collectors.joining(", ")) );
	}
}
