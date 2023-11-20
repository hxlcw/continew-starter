/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.charles7c.continew.starter.core.autoconfigure.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性
 *
 * @author Charles7c
 * @author Lion Li（RuoYi-Vue-Plus）
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 核心/最小线程数（默认：CPU 核心数 + 1）
     */
    private Integer corePoolSize;

    /**
     * 最大线程数（默认：(CPU 核心数 + 1) * 2）
     */
    private Integer maxPoolSize;

    /**
     * 队列容量
     */
    private int queueCapacity = 128;

    /**
     * 活跃时间（单位：秒）
     */
    private int keepAliveSeconds = 300;
}
