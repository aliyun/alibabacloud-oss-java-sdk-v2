package com.aliyun.sdk.service.oss2.retry;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

class BackoffTest {

    @Test
    void testEqualJitterBackoff() {
        Duration min = Duration.ofSeconds(0);
        Duration max = Duration.ofSeconds(10);

        BackoffDelayer delay = new EqualJitterBackoff(
                Duration.ofMillis(200),
                max
        );
        Exception e = new Exception();
        assertThat(delay.backoffDelay(0, e)).isGreaterThan(min);

        int nonZeroCnt = 0;
        for (int i = 0; i < 128; i++) {
            Duration val = delay.backoffDelay(i, e);
            assertThat(val).isGreaterThanOrEqualTo(min);
            assertThat(val).isLessThanOrEqualTo(max);
            if (!val.isZero()) {
                nonZeroCnt++;
            }
        }
        assertThat(nonZeroCnt).isGreaterThan(100);
    }

    @Test
    void testFixedDelayBackoff() {
        BackoffDelayer delay = new FixedDelayBackoff(Duration.ofSeconds(20));
        for (int i = 0; i < 128; i++) {
            assertThat(delay.backoffDelay(i, new Exception())).isEqualTo(Duration.ofSeconds(20));
        }
    }

    @Test
    void testFullJitterBackoff() {
        Duration min = Duration.ofSeconds(0);
        Duration max = Duration.ofSeconds(20);

        BackoffDelayer delay = new FullJitterBackoff(
                Duration.ofMillis(200),
                max
        );
        Exception e = new Exception();
        assertThat(delay.backoffDelay(0, e)).isGreaterThan(min);

        int nonZeroCnt = 0;
        for (int i = 0; i < 128; i++) {
            Duration val = delay.backoffDelay(i, e);
            assertThat(val).isGreaterThanOrEqualTo(min);
            assertThat(val).isLessThanOrEqualTo(max);
            if (!val.isZero()) {
                nonZeroCnt++;
            }
        }
        assertThat(nonZeroCnt).isGreaterThan(100);
    }
}