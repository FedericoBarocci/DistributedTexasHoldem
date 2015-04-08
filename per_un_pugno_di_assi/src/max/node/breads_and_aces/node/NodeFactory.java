package breads_and_aces.node;



public interface NodeFactory {
//	Node create(String nodeId, Player player, ConnectionInfo connectionInfo, Map<String, GameService> map);
	DefaultNode create(String nodeId/*, NodeConnectionInfos connectionInfo*/);
	NodeAsInitializerClientable createAsClientable(String nodeId);
}
