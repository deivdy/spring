package br.com.deivdy.spring.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.deivdy.spring.web.rest.TestUtil;

public class PhonesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phones.class);
        Phones phones1 = new Phones();
        phones1.setId(1L);
        Phones phones2 = new Phones();
        phones2.setId(phones1.getId());
        assertThat(phones1).isEqualTo(phones2);
        phones2.setId(2L);
        assertThat(phones1).isNotEqualTo(phones2);
        phones1.setId(null);
        assertThat(phones1).isNotEqualTo(phones2);
    }
}
