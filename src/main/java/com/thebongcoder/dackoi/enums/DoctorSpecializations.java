package com.thebongcoder.dackoi.enums;

import java.util.Arrays;

public enum DoctorSpecializations {
    ALLERGY_IMMUNOLOGY("Allergy and Immunology"),
    ANESTHESIOLOGY("Anesthesiology"),
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    EMERGENCY_MEDICINE("Emergency Medicine"),
    ENDOCRINOLOGY("Endocrinology"),
    GASTROENTEROLOGY("Gastroenterology"),
    GERIATRICS("Geriatrics"),
    HEMATOLOGY("Hematology"),
    INFECTIOUS_DISEASE("Infectious Disease"),
    INTERNAL_MEDICINE("Internal Medicine"),
    NEPHROLOGY("Nephrology"),
    NEUROLOGY("Neurology"),
    OBSTETRICS_GYNECOLOGY("Obstetrics and Gynecology"),
    ONCOLOGY("Oncology"),
    OPHTHALMOLOGY("Ophthalmology"),
    ORTHOPEDICS("Orthopedics"),
    OTOLARYNGOLOGY("Otolaryngology (ENT)"),
    PEDIATRICS("Pediatrics"),
    PHYSICAL_MEDICINE("Physical Medicine and Rehabilitation"),
    PLASTIC_SURGERY("Plastic Surgery"),
    PSYCHIATRY("Psychiatry"),
    PULMONOLOGY("Pulmonology"),
    RADIOLOGY("Radiology"),
    RHEUMATOLOGY("Rheumatology"),
    SPORTS_MEDICINE("Sports Medicine"),
    GENERAL_SURGERY("Surgery (General Surgery)"),
    THORACIC_SURGERY("Thoracic Surgery"),
    UROLOGY("Urology"),
    VASCULAR_SURGERY("Vascular Surgery"),
    DENTISTRY("Dentistry"),
    PATHOLOGY("Pathology"),
    PODIATRY("Podiatry"),
    CLINICAL_PSYCHOLOGY("Clinical Psychology"),
    NEUROSURGERY("Neurosurgery"),
    MEDICAL_GENETICS("Medical Genetics"),
    OSTEOPATHIC_MEDICINE("Osteopathic Medicine"),
    NUCLEAR_MEDICINE("Nuclear Medicine"),
    FAMILY_MEDICINE("Family Medicine"),
    CRITICAL_CARE("Critical Care Medicine"),
    PAIN_MANAGEMENT("Pain Management"),
    PREVENTIVE_MEDICINE("Preventive Medicine"),
    OCCUPATIONAL_MEDICINE("Occupational Medicine"),
    HYPERBARIC_MEDICINE("Hyperbaric Medicine"),
    FORENSIC_MEDICINE("Forensic Medicine");

    final String value;

    DoctorSpecializations(String value) {
        this.value = value;
    }

    public static DoctorSpecializations getSpecialization(String value) {
        return Arrays.stream(DoctorSpecializations.values())
                .filter(ds -> ds.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    public String getValue() {
        return value;
    }
    }
