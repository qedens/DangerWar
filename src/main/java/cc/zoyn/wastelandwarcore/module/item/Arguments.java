package cc.zoyn.wastelandwarcore.module.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Zoyn
 * @since 2017-12-10
 */
public class Arguments {

    private Map<ArgsType, Object> Args = new EnumMap<>(ArgsType.class);

    public enum ArgsType {
        CD("CD", true),
        Damage("伤害"),
        Recover("回血"),
        Amount("数量", int.class),
        EntityType("类型", org.bukkit.entity.EntityType.class),
        Time("时间", true),
        Chance("几率", true),
        Effect("效果", PotionEffectType.class),
        Level("等级", int.class),
        Target("目标", Entity.class);
        private String Name;
        private Class<?> cls;
        private boolean remove = false;

        private <T> ArgsType(String n) {
            this.Name = n;
            this.cls = float.class;
        }

        private <T> ArgsType(String n, boolean b) {
            this.Name = n;
            this.cls = float.class;
            this.remove = b;
        }

        private <T> ArgsType(String n, Class<? extends T> cls) {
            this.Name = n;
            this.cls = cls;
        }

        public String getName() {
            return Name;
        }

        public Class<?> getCls() {
            return cls;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder().append('(');
        for (Map.Entry<ArgsType, Object> e : Args.entrySet()) {
            sb.append(e.getKey().getName()).append(":").append(e.getValue());
        }
        sb.append(')');
        return sb.toString();
    }

    public Arguments(String v) {
        v = v.split("\\(")[1];
        v = v.replaceAll("\\)", "").replaceAll("：", ":");
        String ss[] = v.split(" ");
        A:
        for (String s : ss) {
            for (ArgsType a : ArgsType.values()) {
                if (s.startsWith(a.getName())) {
                    String val = s.split(":")[1];
                    if (a == ArgsType.Time) {
                        if (val.contains("无限")) {
                            this.Args.put(a, -1);
                            continue A;
                        }
                    }
                    if (a.remove) {
                        val = val.replaceAll("[\u4e00-\u9fa5]", "");
                    }
                    if (a.getCls() == float.class) {
                        if (val.contains("%")) {
                            this.Args.put(a, Float.parseFloat(val.replaceAll("\\%", "")) * 0.01f);
                        } else {
                            this.Args.put(a, Float.parseFloat(val));
                        }
                    } else if (a.getCls() == int.class) {
                        if (val.contains("%")) {
                            this.Args.put(a, Integer.parseInt(val.replaceAll("\\%", "")) * 0.01f);
                        } else {
                            this.Args.put(a, Integer.parseInt(val));
                        }
                    } else if (a.getCls().isEnum()) {
                        if (a.getCls() == org.bukkit.entity.EntityType.class) {
                            @SuppressWarnings("deprecation")
							EntityType e = org.bukkit.entity.EntityType.fromName(val);
                            this.Args.put(a, e);
                            continue A;
                        }
//                        try {
//                            Method m = a.getCls().getMethod("valueOf", String.class);
//                            m.setAccessible(true);
//                            Object o = a.getCls() != EntityType.class ? m.invoke(null, val) : EntityType.fromName(val);
//
//                            this.Args.put(a, o);
//                        } catch (NoSuchMethodException ex) {
//                            Logger.getLogger(Argments.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (SecurityException ex) {
//                            Logger.getLogger(Argments.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (IllegalAccessException ex) {
//                            Logger.getLogger(Argments.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (IllegalArgumentException ex) {
//                            Logger.getLogger(Argments.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (InvocationTargetException ex) {
//                            Logger.getLogger(Argments.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    } else if (a.getCls() == PotionEffectType.class) {
                        @SuppressWarnings("deprecation")
						PotionEffectType pt = PotionEffectType.getById(Integer.parseInt(val));
                        this.Args.put(a, pt);
                        continue A;
                    } else {
                        this.Args.put(a, val);
                    }
                    continue A;
                }
            }
        }
    }

    public void addEntity(Entity e) {
        this.Args.put(ArgsType.Target, e);
    }

    public Arguments(String v, Entity e) {
        this.Args.put(ArgsType.Target, e);
        v = v.split("\\(")[1];
        v = v.replaceAll("\\)", "");
        String ss[] = v.split(" ");
        A:
        for (String s : ss) {
            for (ArgsType a : ArgsType.values()) {
                if (s.startsWith(a.getName())) {
                    String val = s.split(":")[1];
                    if (a.remove) {
                        val = val.replaceAll("[\u4e00-\u9fa5]", "").replaceAll("\\%", "");
                    }
                    if (a.getCls() == float.class) {
                        this.Args.put(a, Float.parseFloat(val));
                    } else if (a.getCls() == int.class) {
                        this.Args.put(a, Integer.parseInt(val));
                    } else if (a.getCls().isEnum()) {
                        try {
                            Method m = a.getCls().getMethod("valueOf", String.class);
                            m.setAccessible(true);
                            this.Args.put(a, m.invoke(null, val));
                        } catch (Exception ex) {
                            Logger.getLogger(Arguments.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        this.Args.put(a, val);
                    }
                    continue A;
                }
            }
        }
    }

    public boolean hasValue(ArgsType a) {
        return this.Args.containsKey(a);
    }

    public float getFloatValue(ArgsType a) {
        if (a.getCls().equals(float.class)) {
            return this.getValue(a);
        }
        return 0f;
    }

    @SuppressWarnings("unchecked")
	public <T> T getValue(ArgsType a) {
        return (T) Args.get(a);
    }

}