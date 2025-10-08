package es.upm.aled.lab2.kinematics;

import es.upm.aled.lab2.gui.Node;

/**
 * This class implements a forward kinematics algorithm using recursion. It
 * expects a tree of Segments (defined by its length and angle with respect to
 * the previous Segment in the tree) and returns a tree of Nodes (defined by
 * their absolute coordinates in a 2-dimensional space).
 * 
 * @author rgarciacarmona
 */
public class ForwardKinematics {

	/**
	 * Returns a tree of Nodes to be used by SkeletonPanel to draw the position of
	 * an exoskeleton. This method is the public facade to a recursive method that
	 * builds the result from a tree of Segments defined by their angle and length,
	 * and the relationship between them (which Segment is children of which).
	 * 
	 * @param root    The root of the tree of Segments.
	 * @param originX The X coordinate for the origin point of the tree.
	 * @param originY The Y coordinate for the origin point of the tree.
	 * @return The tree of Nodes that represent the exoskeleton position in absolute
	 *         coordinates.
	 */
	// Public method: returns the root of the position tree
	public static Node computePositions(Segment root, double originX, double originY) {
		return computePositions(root, originX, originY, 0);
	}

	/**
     * Método recursivo (privado) que calcula la posición final de cada segmento
     * y de todos sus hijos mediante trigonometría.
     *
     * En cada llamada:
     * 
     *  Calcula el punto final del segmento actual.
     *   Crea un nodo en esa posición.
     *  Si el segmento tiene hijos, llama recursivamente al método
     *       para calcular sus posiciones
     *   Si no tiene hijos, se cumple el caso base y termina la recursión.
     *
     * @param link              Segmento actual a procesar.
     * @param baseX             Coordenada X del inicio del segmento.
     * @param baseY             Coordenada Y del inicio del segmento.
     * @param accumulatedAngle  Ángulo acumulado de todos los segmentos anteriores.
     * @return Nodo que representa el punto final de este segmento.
     */
    private static Node computePositions(Segment link, double baseX, double baseY, double accumulatedAngle) {

        // --- Medición de tiempos  ---
        long startTime = System.nanoTime();

        // --- CÓDIGO GENERAL ---
       
        double totalAngle = accumulatedAngle + link.getAngle();

        // Calcula el punto final del segmento usando trigonometría
        double endX = baseX + link.getLength() * Math.cos(totalAngle);
        double endY = baseY + link.getLength() * Math.sin(totalAngle);

        // Crea un nuevo nodo en la posición calculada
        Node currentNode = new Node(endX, endY);

        // --- CASO BASE ---
        
        if (link.getChildren().isEmpty()) {
            long runningTime = System.nanoTime() - startTime;
            System.out.println("Tiempo de computePositions para un segmento sin hijos: " 
                    + runningTime + " ns");
            return currentNode;
        }

        // --- PASO RECURSIVO ---
        
        for (Segment child : link.getChildren()) {
            // Llamada recursiva: el extremo actual se convierte en la base del hijo
            Node childNode = computePositions(child, endX, endY, totalAngle);
            // Añade el nodo hijo al nodo actual
            currentNode.addChild(childNode);
        }

        // Medición de tiempo para segmentos con hijos
        long runningTime = System.nanoTime() - startTime;
        System.out.println("Tiempo de computePositions para un segmento con " 
                + link.getChildren().size() + " hijos: " + runningTime + " ns");

        // Devuelve el nodo que representa el final de este segmento
        return currentNode;
    }
}