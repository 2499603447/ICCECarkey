/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.manager.active;

/**
 * Constant values
 *
 * @since 2020-02-18
 */
public class Constants {
    /**
     * Public key of wallet server
     */
    public static final String WALLET_PUBLIC_KEY = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEA"
        + "5w4ii7rh2J6B371m02C+FBFgp9vZ8aS6nv5nBdAaMWpETj6bzZ1rFiGFF99o7qSbbh63cKgic0dARMed6HC"
        + "lOC5QGB7o16FwZGemrjN/z8/4dXc5OcUVs8BuebeJC4OvKTT7roQ7p9GuGgg1jrGgFkfC+nkWGt3ePE0tiX"
        + "JfH5f341X1OyKHSjdF5N6QYM0m73+XxS1foq3IyyIHrjq4I9uH1QjpFOF+9jo35bAYcNUquONUd6J55gAc/"
        + "Cztje6KfNmKBz24zwwoRkbXmNcaphK/t/z5N3FZsiS70GFv6iABXBR7j6ceaWzs24oQ2A1LuakXNN2ORnVn"
        + "j8laSxlMpey5Ci+eicU+mL1ivixGDmDhCgT4u4tj4vvD/AV+z88nUR83muwZ3+J3ASBFZVaBrMeeX6o+8rn"
        + "+iK+WvP5SLL2DLpffiMX0QqwA6C/RVW8KDh8Jq9yNkE0Uy81yVO0BJctY6t35d0fucLmwTa2YMSqV12dDfe"
        + "MZjxMDhmJVDUD/AgMBAAE=";

    /**
     * Public key of sp server
     */
    public static final String SERVER_PUBLIC_KEY =
        "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAirME13zanj9JoqwanHdUDQGtmRyluJn8EBZNZ6jtvDVp4hKN4ipTFMs7giOaRxrcL7pCdnDyrxvojcS9WOfKlXpUSVAJIAj2WaHpYGfNMMRJS8H29lZ3NLhZZ8cIZn94P1qMNKwR5V6+5EdV5A/sxfZw37cvsYGT+Iv2uHXOnXPWr7SuKzGd/uYfxO9nzQN0M27WHA/6+zlA3gbAcXyneVmQfsOlV+TwWzA5P4UWFTUBq/uOE9UGcoZn3TJbVBcAENvJlB6eYdtUH0W8Wu0+MDmRxf00p7z4FyiK+WgsefUOCdOp6XkCD+rjv7Fjp34jZiIopIdNVBUtH2pYz1rJeb0ZC5V7s56kyDlndK/P1qSMp4n4vNvzvGlWb1KOgaxRmTp4AuukS/Wn7NK9dR19UWmlXnxfF6ScoSp0CqUtTHeh1f3kZPMdDZq4RmrxdZ2W6zHODcME+wEFBtAeumBA+uYNXdvl10auoZu9Vcotlog2ntSKiIdPJGXuuVc4VNTHpPEnsWfWTx+DbAIsmXGLXCZIoRsKut1iumykEdvZkHq/ZsUwv/uX5+tryEazOyle1R5/Uv7AwohhGfyHnWBs26jztsqRx0Gfcn0X+Ayn3gx6RHJfXo6uyM1EBE5a+Bfzm9Y4mqVUMQGb8DAO8gD8DpOLhvSmRqVVQtDwTrvPCiUCAwEAAQ==";

    /**
     * Private key of sp server
     */
    public static final String SERVER_SECRET_KEY =
        "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQCKswTXfNqeP0mirBqcd1QNAa2ZHKW4mfwQFk1nqO28NWniEo3iKlMUyzuCI5pHGtwvukJ2cPKvG+iNxL1Y58qVelRJUAkgCPZZoelgZ80wxElLwfb2Vnc0uFlnxwhmf3g/Wow0rBHlXr7kR1XkD+zF9nDfty+xgZP4i/a4dc6dc9avtK4rMZ3+5h/E72fNA3QzbtYcD/r7OUDeBsBxfKd5WZB+w6VX5PBbMDk/hRYVNQGr+44T1QZyhmfdMltUFwAQ28mUHp5h21QfRbxa7T4wOZHF/TSnvPgXKIr5aCx59Q4J06npeQIP6uO/sWOnfiNmIiikh01UFS0faljPWsl5vRkLlXuznqTIOWd0r8/WpIynifi82/O8aVZvUo6BrFGZOngC66RL9afs0r11HX1RaaVefF8XpJyhKnQKpS1Md6HV/eRk8x0NmrhGavF1nZbrMc4NwwT7AQUG0B66YED65g1d2+XXRq6hm71Vyi2WiDae1IqIh08kZe65VzhU1Mek8SexZ9ZPH4NsAiyZcYtcJkihGwq63WK6bKQR29mQer9mxTC/+5fn62vIRrM7KV7VHn9S/sDCiGEZ/IedYGzbqPO2ypHHQZ9yfRf4DKfeDHpEcl9ejq7IzUQETlr4F/Ob1jiapVQxAZvwMA7yAPwOk4uG9KZGpVVC0PBOu88KJQIDAQABAoICACB/HyRJ6YLoOx11C2sbfkLp4L2d+6oL0gYG463k44aUUTGZvUp/NiYKE/BC3TPJtEsubcIIZqPmew80SFTbQhjRp4vhx7GMyANqLx7aUzYY1z97u5wt2BIvGyt4c5xoQ0k8strocez2zOz5VcNwg75CJmIULoV9Yaj7Shwf5f/4ExXp1GDkaCs/ybO/j+d15rTiRCIhEGHqISukE1TDqw2AkZ1Rw5ybVEFZRCCA0xK276RW9Qvqfm841H77/G/wT6CjjlA4+EzfMY9+LEp1iXuNEX18/OhMGyh55E3ZPYp5gweEYywDoRsPorrYGgw1PqAAYtthpBmOOJtCtHoowEl9qsQfIqrdiz2NT00k90IUTA+wLHFLmTs22UB2jkNOqEf1pFqgfNvrt7CkuRQoeaRcnLmzb4SGXTIHiXCMiqxgdJMk4bNPWEmdvAQ+ILx3WZFEqLAz7bMb8DhAdJhf34o5sugCQxJLgtFIm2JM/sqpzuDI4ZT+zrlAF57Nyxd8jFtx1PFv4xwNC+kaiig2q+vSqeshrpZ0dkNHMax+Mf+thJ25krSdHEQa+LOloG/qzxkGKVYtt3raF0zFGShAsKni76PyYo38+49K02EkGRKdexkrXizpeJBxWDjtX8Gwta3OPHhkie5HFLRfTzLI3FzvjnjpTOFgMkxr3mizY/FBAoIBAQDxBmV0y9wR81dL4kuweRznAjNJ6Jwi2qXULMcp0LcCWADjT+FzXocOceHHy0S3/vOBn02KQUxWsTZMTdsHWbinhK8K1ybKk1x6YPvx6w6fMe+SVWqKfD9CU/AGIA51FO3hIvs2e1aXkR1Pq6FakwCI8hftuOXdYJ36B7PzKXr9pFTUCzQvbLfdUnkgIT64bZ0v8if1+qoUzvwucFpHjvYAnppyXf4iOSW8T2wskpRPUk9XwbkknePi5f5bL5EkDte+Yo3+U4DE8m4b73HSHb+0BBQikGRMmbNDibcKunklFxWMjgMQURXtrqNNpqv6e8tG4WyrAwHhmcsmSMtvufC1AoIBAQCTURbmPPAaozfd4ll9+Sk3qV1J8obsGxeMXmb1MiG+Xy89qO9SorFNyMjuV2QBB+oQfEQgXHLT2ee6w157aVxgRz07fpwfG7CovdMdZUGyYsWaAOah64gVh/04LughZCJWvXE2+o6UMCBS/l3igaIsqCIBkkzYlb7Gt6yOFxDML939dhBWdpAR/im+S4eGHTABh3FMg3vzbdoPoz3xHQpr6yjzVcNvnv4VOORf0MRGHf9pXvKlkyWTn18+4ThNfap+bEygL+5dtOX/HXzq7T0pvAz1ikAjA6nJq7nSqX9spRkR/mAlr+pDAl3F43ZKRUfJHWYqh/cSQ2bCIajprEmxAoIBAQDooAjJ+nwT4y5NMkNlNu+bUndYDSatSSSmLPWouSX5ICDzQ8TQCdsGKQoGQHy5rVsurycymiVowq8qZDZS2Nis7hXXu6JYAHRQNUzVx82b/qFANiqXMKYJiWPvNX9ep22UTqPxiIch0FBe9cLM3Xa1ths8OKm6yaaPjGG0/kMTjUjGQ24Bm8e12ATJ6uxTKpB8lxbUhTMk1EV3OL8Zs7RhErL0UR2XwFpYbrt0JaD5EfM8OuBLpAwvkVW2gqluCNlXil2LquJss6NsWOUdK9M7lngsh/oWKH5pYACHXUTRdH864lG4rNoiRdyddJEmN9cROT8dqtBlAn+dGsMmuWrFAoIBABnhH+24fNGhJLpU/jW0JLqDhUDtWyh1JfDh0KJ4eRcRb6IcYFabABzTDgFY1tebhVH6EpnAw+dVm3Diy2FQxAqh2S7vcgvA+IrcNg8u75if42jHibyePNzTc/d/vgXHqoSkaRQodg2wWq1ZxDJ7ZXsE2lx+UAOoYshUEfswMCI+snfJWttlnq+af8jPSfKUhA9wtMWlP4wnGyyuF76YxHDq8dm1kky0mJwvz2bJrQFIWcswA950eCBz2e6Y8CQk1GO7MMbfRLevhMwJaR14KcuAHqKv885y4UvtYAqS/jCe3EWd81QLkFLPQpv19pzbiN3azUzhb9UEhDPMZ/kM7DECggEAAzQDQRfHeo1HNpe22Oo373C647SWIm/A4pzrKJYGCXI2xq29v82ZSj9xZlPCNxDcuwr4AqAyz6FDUPnuTWnClsi40b1dczrp5/2EEgaO0gNb/KGRHbyCp5Pgr1xLZMPzTaYEUDnrBcU3ag2EcWNrlPNP7t7LPlBWpYEMKmUllBqVbxTtsYV8BnzzfnGXmF+x9DwAolS7MS23b26JXSh9wv8vbMFwXpsO8zVewx5hkepbEZIbsD9foVQ9+4s124YBDYl0MZvUrz0e4152IJXnMefMsVs89vPNAYvRAcSf82u64Rme8nPhWfxxzRXXIswsVi/S9BrLlKWNLu8HembixQ==";

    /**
     * UTF-8 encode format
     */
    public static final String UTF8_ENCODING = "UTF-8";

    /**
     * Key value of token in header of request
     */
    public static final String TOKEN_KEY_VALUE = "Authorization";

    /**
     * Prev part of token from request
     */
    public static final String TOKEN_PREV_PART = "HuaweiPassToken ";

    /**
     * key of code_ok
     */
    public static final int RESULT_CODE_OK = 0;

    /**
     * key of param error
     */
    public static final int RESULT_CODE_PARAM_ERROR = 1;

    /**
     * key of sign error
     */
    public static final int RESULT_CODE_SIGN_ERROR = 2;

    /**
     * key of inner error
     */
    public static final int RESULT_CODE_INNER_ERROR = 3;
}
