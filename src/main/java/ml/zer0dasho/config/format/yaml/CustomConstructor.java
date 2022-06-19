package ml.zer0dasho.config.format.yaml;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CustomConstructor extends Constructor {
	
	private Object constructCS(Map<Object, Object> map) {
		
		Map<String, Object> result = Maps.newLinkedHashMap();
		
		map.entrySet().forEach(e -> {
			
			Object k = e.getKey();
			Object v = e.getValue();
			
			result.put(k.toString(), v);
			
			if(v instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> value = (Map<Object, Object>) v;
				
				if(value.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY))
					result.put(k.toString(), constructCS(value));
			}
			
			else if(v instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> value = (List<Object>) v;
				List<Object> newValue = Lists.newLinkedList();
				
				value.forEach(o -> {
					if(o instanceof Map) {
						@SuppressWarnings("unchecked")
						Map<Object, Object> m = (Map<Object, Object>) o;
						
						boolean isCS = m.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY);
						newValue.add(isCS ? constructCS(m) : m);
					}
					
					else
						newValue.add(o);
				});
				
				result.put(k.toString(), newValue);
			}
		});
		
		return ConfigurationSerialization.deserializeObject(result);
	}
	
	@Override
	protected Object constructObject(Node node) {
		
		try {
			if(node.getType().asSubclass(ConfigurationSerializable.class) != null) {
				
				if(node instanceof MappingNode) {
					Map<Object, Object> cs = super.constructMapping((MappingNode) node);
					
					if(cs.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY))
						return constructCS(cs);
				}
				
				else if(node instanceof SequenceNode) {
					List<Node> nodes = ((SequenceNode) node).getValue();
					
					for(Node n : nodes) {
						if(n instanceof MappingNode) {
							Map<Object, Object> cs = super.constructMapping((MappingNode) n);
							
							if(cs.containsKey(ConfigurationSerialization.SERIALIZED_TYPE_KEY))
								return constructCS(cs);
						}
					}
				}
			}
			
		} catch(Exception ex) {}
		

		return super.constructObject(node);
	}
}
