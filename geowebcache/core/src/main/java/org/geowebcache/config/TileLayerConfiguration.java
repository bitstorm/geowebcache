/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Arne Kepp, The Open Planning Project, Copyright 2008
 *  
 */
package org.geowebcache.config;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.geowebcache.GeoWebCacheExtensions;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.layer.TileLayerDispatcher;

/**
 * A layer provider for {@link TileLayerDispatcher}.
 * <p>
 * Implementations must be singletons discoverable through {@link GeoWebCacheExtensions} (i.e.
 * spring beans)
 * </p>
 */
public interface TileLayerConfiguration extends BaseConfiguration {

    /**
     * @return an unmodifiable list of layers, may be empty, but not null.
     * @deprecated use {@link #getLayers()}
     */
    @Deprecated
    List<? extends TileLayer> getTileLayers();

    /**
     * Get all {@link TileLayer}s included in a TileLayerConfiguration.
     *
     * @return an unmodifiable list of layers, may be empty, but not null.
     */
    Iterable<? extends TileLayer> getLayers();

    /**
     * Gets a single {@link TileLayer} from the configuration, using the layer's unique name as a key.
     *
     * @param layerName the layer name
     * @return the layer named {@code layerIdent} or {@code null} if no such layer exists in this
     *         configuration
     */
    TileLayer getLayer(String layerName);

    /**
     * @param layerName
     *            the layer name
     * @return the layer named {@code layerIdent} or {@code null} if no such layer exists in this
     *         configuration
     * @deprecated use {@link #getLayer(String)}
     */
    @Deprecated
    TileLayer getTileLayer(String layerName);

    /**
     * @param layerId
     *            the layer identifier
     * @return the layer identified by {@code layerId} or {@code null} if no such layer exists in
     *         this configuration
     * @deprecated use {@link #getLayer(String)}
     */
    @Deprecated
    TileLayer getTileLayerById(String layerId);

    /**
     * Get the number of TileLayers configured
     *
     * @return the number of {@link TileLayer}s configured
     */
    int getLayerCount();

    /**
     * Get the number of TileLayers configured
     * @return
     * @deprecated use {@link #getLayerCount()}
     */
    @Deprecated
    int getTileLayerCount();

    /**
     * Get the names of all TileLayers configured
     *
     * @return The set of all TileLayers configured. May be empty, but not null.
     */
    Set<String> getLayerNames();

    /**
     * Get the names of all TileLayers configured
     * @return
     * @deprecated use {@link #getLayerNames()}
     */
    @Deprecated
    Set<String> getTileLayerNames();

    /**
     * Removes the given tile layer from this configuration
     * @param layerName name of the layer to remove
     * @return {@code true} if the layer was removed, {@code false} if no such layer exists
     * @throws IllegalArgumentException If this configuration is not able to remove the layer.
     */
    boolean removeLayer(String layerName);

    /**
     * Replaces an existing tile layer of the same name with this tile layer.
     *
     * @param tl the modified tile layer. Its name must be the same as a tile layer that already exists.
     * @throws NoSuchElementException If no tile layer with a matching name exists.
     */
    void modifyLayer(TileLayer tl) throws NoSuchElementException;

    /**
     * Adds the given tile layer to this configuration, provided
     * {@link #canSave(TileLayer) canSave(tl) == true}.
     * 
     * @param tl the tile layer to add to the configuration
     * @throws IllegalArgumentException
     *             if this configuration is not able of saving the specific type of layer given, or
     *             if any required piece of information is missing or invalid in the layer (for
     *             example, a missing or duplicated name or id, etc).
     */
    void addLayer(TileLayer tl) throws IllegalArgumentException;

    /**
     * Whether a tile layer with the given name exists in the configuration.
     *
     * @param layerName The name of of the layer
     * @return true if the layer exists, false otherwise
     */
    boolean containsLayer(String layerName);

    /**
     * If this method returns TRUE WMTS service implementation will strictly comply with the
     * correspondent CITE tests.
     *
     * @return TRUE or FALSE, activating or deactivation CITE strict compliance mode for WMTS
     */
    default boolean isWmtsCiteCompliant() {
        // by default CITE tests strict compliance is not activated
        return false;
    }

    /**
     * Whether the configuration is capable of saving the provided tile layer.
     *
     * @param tl a tile layer to be added or saved
     * @return {@code true} if this configuration is capable of saving the given tile layer,
     *         {@code false} otherwise (usually this check is based on an instanceof check, as
     *         different configurations may be specialized on different kinds of layers).
     */
    boolean canSave(TileLayer tl);
}
