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
 * @author Marius Suta / The Open Planning Project 2008 
 */
package org.geowebcache.rest;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;

public class MTSeeder implements Callable<GWCTask> {
    private static Log log = LogFactory.getLog(org.geowebcache.rest.MTSeeder.class);
    
    private GWCTask task= null;
    
    public MTSeeder(GWCTask task){
        this.task = task;
    }
    public GWCTask call() {
        try {
            task.doAction();
        } catch(GeoWebCacheException gwce) {
            log.error(gwce.getMessage());
        }
        return task;
    }
}