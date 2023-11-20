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

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import top.charles7c.continew.starter.core.exception.BaseException;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 异步任务自动配置
 *
 * @author Charles7c
 * @author Lion Li（RuoYi-Vue-Plus）
 * @since 1.0.0
 */
@Slf4j
@Lazy
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "thread-pool", name = "enabled", havingValue = "true")
@EnableAsync(proxyTargetClass = true)
public class AsyncAutoConfiguration implements AsyncConfigurer {

    private final ScheduledExecutorService scheduledExecutorService;

    public AsyncAutoConfiguration(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /**
     * 异步任务 @Async 执行时，使用 Java 内置线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        log.info("[ContiNew Starter] - Auto Configuration 'AsyncConfigurer' completed initialization.");
        return scheduledExecutorService;
    }

    /**
     * 异步任务执行时的异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message: ").append(throwable.getMessage()).append(", Method name: ")
                .append(method.getName());
            if (ArrayUtil.isNotEmpty(objects)) {
                sb.append(", Parameter value: ").append(Arrays.toString(objects));
            }
            throw new BaseException(sb.toString());
        };
    }
}
