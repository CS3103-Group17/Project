/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

/**
 *
 * @author Christian Morgner
 */
public class Sort extends ParameterBase {

	public Sort(final SortKey sortKey) {
		super("sort", sortKey.name());
	}
}
