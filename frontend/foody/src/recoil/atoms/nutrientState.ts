import { atom } from 'recoil';

// 전체영양소 상태를 저장
const nutrientState = atom({
	key: 'nutrientState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	}, // on일 경우 true
});

export const breakfastState = atom({
	key: 'breakfastState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
});

export const lunchState = atom({
	key: 'lunchState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
});

export const dinnerState = atom({
	key: 'dinnerState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
});

export const snackState = atom({
	key: 'snackState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
});
export default nutrientState; // 변수를 기본 내보내기로 설정