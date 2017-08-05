package com.zihai.activiti.util;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> initials = new ArrayList<Node>();
    
    public Node getInitial(){
    	if(initials.size()>0){
    		return initials.get(0);
    	}
    	return null;
    }

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<Node>();
        for(Node initial:initials){
        	 visitNode(initial, nodes);
        }
        return nodes;
    }

    public void visitNode(Node node, List<Node> nodes) {
        nodes.add(node);

        for (Edge edge : node.getEdges()) {
            Node nextNode = edge.getDest();
            visitNode(nextNode, nodes);
        }
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<Edge>();
        for(Node initial:initials){
        	 visitEdge(initial, edges);
       }       
        return edges;
    }

    public void visitEdge(Node node, List<Edge> edges) {
        for (Edge edge : node.getEdges()) {
            edges.add(edge);

            Node nextNode = edge.getDest();
            visitEdge(nextNode, edges);
        }
    }

    public Node findById(String id) {
        for (Node node : this.getNodes()) {
            if (id.equals(node.getId())) {
                return node;
            }
        }

        return null;
    }

	public void addInitial(Node currentNode) {
		initials.add(currentNode);
		
	}
}
