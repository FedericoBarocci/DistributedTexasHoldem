/** Describes a message */
public class Message implements java.io.Serializable {
	/** Source of message */
	RemoteClient source;
	/** sequential number for initiating client */
	int id;
	/** Contents of message */
	String txt;

	public Message(RemoteClient s, int i, String t) {
			source = s;
			id = i;
			txt = t;
			}

	public int hashCode() {
		return source.hashCode() + id;
		}

	public boolean equals(Object that) {
		if (!(that instanceof Message)) return false;
		Message m2 = (Message)that;
		return id == m2.id && source.equals(m2.source);
		}
	}
